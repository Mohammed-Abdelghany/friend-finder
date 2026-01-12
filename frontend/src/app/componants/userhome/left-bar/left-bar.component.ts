import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {ProfileService} from '../../../../services/profile.service';
import {Profile} from '../../../../Responses/ProfileRes';

@Component({
  selector: 'app-left-bar',
  templateUrl: './left-bar.component.html',
  styleUrls: ['./left-bar.component.css']
})
export class LeftBarComponent implements OnInit {
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
