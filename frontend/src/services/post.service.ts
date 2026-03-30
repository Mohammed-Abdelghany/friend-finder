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
  private apiUrl = 'http://localhost:9090/api/posts';
  constructor(private http: HttpClient) {
  }

  getPosts(page: number, size: number): Observable<Page<PostModel>> {
    return this.http.get<Page<PostModel>>(
      `http://localhost:9090/api/posts?page=${page}&size=${size}`
    );
  }

  createPost(content: string, file?: File): Observable<PostModel> {
    const formData = new FormData();
    formData.append('data', new Blob([JSON.stringify({ content })], { type: 'application/json' }));
    if(file) {
      formData.append('file', file);
    }

    return this.http.post<PostModel>(this.apiUrl, formData);
  }
  }
