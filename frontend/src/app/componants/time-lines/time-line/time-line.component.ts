import { Component, OnInit } from '@angular/core';
import {PostService} from '../../../../services/post.service';
import {PostModel} from '../../../model/postmodel';
import {Subscription} from 'rxjs';

import {Page} from '../../../model/page';
import {Profile} from '../../../../Responses/ProfileRes';
import {ProfileService} from '../../../../services/profile.service';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-time-line',
  templateUrl: './time-line.component.html',
  styleUrls: ['./time-line.component.css'],

})
export class TimeLineComponent implements OnInit {
  posts: PostModel[] = [];
  currentPage = 1;
  pageSize = 5;
  totalPages = 0;

  user: Profile;
  srcImg = 'http://localhost:9090/assets';
  constructor(private postservice: PostService , private profileService: ProfileService) { }

  ngOnInit(): void {
    this.loadPosts();
    this.getProfile();

  }


  loadPosts(page: number = this.currentPage): void {
    this.postservice.getPosts(page, this.pageSize).subscribe({

      next: (pageData: Page<PostModel>) => {

        this.posts = pageData.content.map(post => ({
          ...post,
          CreatedAt: post.CreatedAt ? post.CreatedAt : new Date(),

        }));
        this.currentPage = pageData.number + 1; // backend غالبًا يبدأ من 0
        this.totalPages = pageData.totalPages;
        console.log('Posts loaded successfully', this.posts);
      },

      error: (err) => {
        console.error('Error loading posts', err);
      },
    });
  }
  goToPage(page: number): void {
    if (page < 1 || page > this.totalPages) { return; }
    this.currentPage = page;
    this.loadPosts(page);
  }
  nextPage(): void {
    this.goToPage(this.currentPage + 1);
  }
  prevPage(): void {
    this.goToPage(this.currentPage - 1);
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
