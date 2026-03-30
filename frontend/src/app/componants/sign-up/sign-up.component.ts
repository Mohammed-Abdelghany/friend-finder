import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import Swal from 'sweetalert2';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  username = '';
  email = '';
  password = '';
  bio = '';

  imgFile: File | null = null;
  coverFile: File | null = null;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {}

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length) {
      this.imgFile = input.files[0];
    }
  }

  onCoverSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length) {
      this.coverFile = input.files[0];
    }
  }

  uploadAndRegister(): void {
    if (!this.imgFile || !this.coverFile) {
      Swal.fire('Error', 'Please select both profile and cover images', 'error');
      return;
    }

    const userData = {
      username: this.username,
      email: this.email,
      password: this.password,
      bio: this.bio
    };

    this.authService.register(userData, this.imgFile, this.coverFile).subscribe({
      next: (user: any) => {
        if (!user?.id) {
          Swal.fire('Error', 'User ID not returned from server', 'error');
          return;
        }
        this.showSuccessAndNavigate();
      },
      error: (err) => {
        Swal.fire({
          title: 'Registration Failed ❌',
          text: err?.error?.message || 'Something went wrong, please try again',
          icon: 'error'
        });
      }
    });
  }

  private showSuccessAndNavigate(): void {
    Swal.fire({
      title: 'Account Created 🎉',
      text: 'Your account has been created successfully',
      icon: 'success',
      width: 500,
      timer: 1800,
      showConfirmButton: false,
      customClass: { popup: 'big-swal' }
    });

    setTimeout(() => {
      this.router.navigate(['/login'], { queryParams: { username: this.username } });
    }, 1500);
  }
}
