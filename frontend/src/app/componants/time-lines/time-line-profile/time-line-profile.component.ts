import { Component, OnInit } from '@angular/core';
import {Profile} from '../../../../Responses/ProfileRes';
import {Router} from '@angular/router';
import {ProfileService} from '../../../../services/profile.service';

@Component({
  selector: 'app-time-line-profile',
  templateUrl: './time-line-profile.component.html',
  styleUrls: ['./time-line-profile.component.css']
})
export class TimeLineProfileComponent implements OnInit {

  user: Profile;
  srcImg = 'http://localhost:9090/assets';
  constructor(private router: Router, private profileService: ProfileService) { }

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
