import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Profile} from '../Responses/ProfileRes';

@Injectable({
  providedIn: 'root'
})

export class ProfileService {

  constructor(private http: HttpClient) {
  }

getUserProfile(): Observable<any> {
  return this.http.get<Profile>('http://localhost:9090/profile');
}

}
