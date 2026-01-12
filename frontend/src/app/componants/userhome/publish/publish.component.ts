import { Component, OnInit } from '@angular/core';
import {ProfileService} from '../../../../services/profile.service';
import {Profile} from '../../../../Responses/ProfileRes';

@Component({
  selector: 'app-publish',
  templateUrl: './publish.component.html',
  styleUrls: ['./publish.component.css']
})
export class PublishComponent implements OnInit {
user: Profile;
  srcImg = 'http://localhost:9090/assets';

  constructor(private profileService: ProfileService) { }
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

}
