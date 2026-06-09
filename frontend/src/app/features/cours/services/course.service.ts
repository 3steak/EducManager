import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { environment } from '../../../../environments/environment';
import { Course } from '../../../shared/models/course.model';

@Injectable({
  providedIn: 'root',
})
export class CourseService {
  private url = environment.apiUrl + '/courses';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Course[]> {
    return this.http.get<Course[]>(this.url);
  }
}
