import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiService } from '../../../core/services/api.service';
import { Course } from '../../../shared/models/course.model';

@Injectable({
  providedIn: 'root',
})
export class CourseService {
  private readonly resourcePath = '/courses';

  constructor(private readonly api: ApiService) {}

  getAll(): Observable<Course[]> {
    return this.api.get<Course[]>(this.resourcePath);
  }

  create(course: Course): Observable<Course> {
    return this.api.post<Course>(this.resourcePath, course);
  }
}
