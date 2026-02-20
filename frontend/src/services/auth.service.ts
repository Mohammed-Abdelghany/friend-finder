import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = 'http://localhost:9090/auth';
  private isValid: { valid: boolean; message?: string };

  constructor(private http: HttpClient) {}

  login(data: { username: string; password: string }): Observable<any> {
    return this.http.post<any>(this.baseUrl + '/login', data)
      .pipe(
        tap(response => {
          if (response && response.token) {
            localStorage.setItem('token', response.token);
          }
        })
      );
  }

  // tslint:disable-next-line:max-line-length
  register(data: { username: string; email: string; password: string; bio: string; profileImagePath: string; profileCoverPath: string }): Observable<any> {
    this.isValid = this.validateInputs(data.username, data.email, data.password);
    if (!this.isValid.valid) {
      throw new Error(this.isValid.message);
    }
    return this.http.post<any>(this.baseUrl + '/register', data);
  }

  // 🔹 إضافة updateUser لتحديث روابط الصور بعد التسجيل
  updateUser(userId: number, data: { profileImagePath?: string; profileCoverPath?: string }): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/update/${userId}`, data);
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  validateInputs(username: string, email: string, password: string): { valid: boolean; message?: string } {
    const usernamePattern = /^[a-zA-Z0-9_]{3,20}$/;
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;

    // if (!usernamePattern.test(username)) {
    //   return { valid: false, message: 'Username must be 3-20 characters long and can include letters, numbers, and underscores.' };
    // }
    //
    // if (!emailPattern.test(email)) {
    //   return { valid: false, message: 'Invalid email format.' };
    // }
    // if (!passwordPattern.test(password)) {
    //   return { valid: false, message: 'Password must be at least 8 characters long and include at least one letter and one number.' };
    // }

    return { valid: true };
  }

  uploadImage(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<{ url: string }>(
      `${this.baseUrl}/upload`,
      formData
    );
  }
}
