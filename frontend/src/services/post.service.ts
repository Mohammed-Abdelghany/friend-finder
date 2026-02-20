import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Profile} from '../Responses/ProfileRes';
import {PostModel} from '../app/model/postmodel';
import {Page} from '../app/model/page';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) { }

  getPosts(page: number, size: number): Observable<Page<PostModel>> {
    return this.http.get<Page<PostModel>>(
      `http://localhost:9090/api/posts?page=${page}&size=${size}`
    );
  }
}
