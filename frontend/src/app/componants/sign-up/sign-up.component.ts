import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../../services/auth.service';
import {Router} from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
username: string;
  email: string;
  password: string;
  bio: string;
  profileImagePath: string;
  constructor(private authService: AuthService, private router: Router) { }
  imgFile: File  | null = null;

  ngOnInit(): void {
  }
  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length) {
      this.imgFile = input.files[0];
    }
  }

  uploadAndRegister(): void {
    if (!this.imgFile) {
      this.imgFile = null;
      return;
    }

    this.authService.uploadImage(this.imgFile).subscribe({
      next: (res: any) => {
        this.profileImagePath = res.url;
        this.register();
      },
      error: () => Swal.fire('Error', 'Failed to upload image', 'error')
    });
  }
  register(): void {
    this.authService.register({
      username: this.username,
      email: this.email,
      password: this.password,
      bio: this.bio,
      profileImagePath: this.profileImagePath || ''
    }).subscribe(
      () => {

        Swal.fire({
          title: 'Account Created 🎉',
          text: 'Your account has been created successfully',
          icon: 'success',
          width: 500,
          timer: 1800,
          showConfirmButton: false,
          customClass: {
            popup: 'big-swal'
          }
        });

        setTimeout(() => {
          this.router.navigate(['/login'], {
            queryParams: { username: this.username }
          });
        }, 1500);
      },
      err => {
        console.error('Registration failed', err);

        Swal.fire({
          title: 'Registration Failed ❌',
          text: err?.error?.message || 'Something went wrong, please try again',
          icon: 'error',
          width: 500,
          confirmButtonText: 'Try Again',
          customClass: {
            popup: 'big-swal'
          }
        });
      }
    );
  }

}
