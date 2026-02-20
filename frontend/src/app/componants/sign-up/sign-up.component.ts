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
  profileImagePath = '';
  profileCoverPath = '';

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
    // Step 1: register user first
    this.authService.register({
      username: this.username,
      email: this.email,
      password: this.password,
      bio: this.bio,
      profileImagePath: '',
      profileCoverPath: ''
    }).subscribe({
      next: (user: any) => {
        if (!user?.id) {
          Swal.fire('Error', 'User ID not returned from server', 'error');
          return;
        }

        const uploadRequests = [];

        // Step 2: Upload profile image if exists
        if (this.imgFile) {
          uploadRequests.push(
            this.authService.uploadImage(this.imgFile).pipe(catchError(() => of(null)))
          );
        } else {
          uploadRequests.push(of(null));
        }

        // Step 3: Upload cover image if exists
        if (this.coverFile) {
          uploadRequests.push(
            this.authService.uploadImage(this.coverFile).pipe(catchError(() => of(null)))
          );
        } else {
          uploadRequests.push(of(null));
        }

        // Step 4: Wait for uploads and then update user record
        forkJoin(uploadRequests).subscribe((results: any[]) => {
          const updatedData: any = {};
          if (results[0]?.url) { updatedData.profileImagePath = results[0].url; }
          if (results[1]?.url) { updatedData.profileCoverPath = results[1].url; }

          // Only update if there's something to update
          if (Object.keys(updatedData).length === 0) {
            this.showSuccessAndNavigate();
            return;
          }

          this.authService.updateUser(user.id, updatedData).subscribe({
            next: () => this.showSuccessAndNavigate(),
            error: () => Swal.fire('Error', 'Failed to update user images', 'error')
          });
        });
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
