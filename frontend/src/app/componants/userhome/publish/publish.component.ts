import {Component, OnInit, ViewChild, ElementRef, Output, EventEmitter} from '@angular/core';
import { ProfileService } from '../../../../services/profile.service';
import { Profile } from '../../../../Responses/ProfileRes';
import {PostService} from '../../../../services/post.service';

@Component({
  selector: 'app-publish',
  templateUrl: './publish.component.html',
  styleUrls: ['./publish.component.css']
})
export class PublishComponent implements OnInit {

  @ViewChild('imageInput') imageInput!: ElementRef<HTMLInputElement>;
  @ViewChild('videoInput') videoInput!: ElementRef<HTMLInputElement>;
  @Output() postCreated = new EventEmitter<void>();
  user!: Profile;
  srcImg = 'http://localhost:9090/assets';
  selectedFile?: File;
  fileType?: 'image' | 'video';
  postContent = '';
  fileName = '';

  constructor(private profileService: ProfileService,private postService: PostService) { }

  ngOnInit(): void {
    this.getProfile();
  }

  getProfile(): void {
    this.profileService.getUserProfile().subscribe({
      next: (profile) => {
        this.user = profile;
      },
      error: (err) => {
        console.error('Error loading profile', err);
      }
    });
  }

  // tslint:disable-next-line:typedef
  triggerFile(type: 'image' | 'video') {
    if(type === 'image') {
      this.imageInput.nativeElement.click();
    } else {
      this.videoInput.nativeElement.click();
    }
  }

  // tslint:disable-next-line:typedef
  onFileSelected(event: any, type: 'image' | 'video') {
    if(event.target.files.length > 0) {
      this.selectedFile = event.target.files[0];
      this.fileType = type;
      this.fileName = this.selectedFile.name;
      console.log('Selected file:', this.selectedFile, 'Type:', type);
    }
  }

  // tslint:disable-next-line:typedef
  submitPost() {
    this.postService.createPost(this.postContent, this.selectedFile).subscribe({
      next: (res) => {
        console.log('Post created!', res);
        // تنظيف النموذج بعد النشر
        this.postContent = '';
        this.selectedFile = undefined;
        this.fileType = undefined;
        this.postCreated.emit();

      },
      error: (err) => {
        console.error('Error creating post', err);
      }
    });
  }
}
