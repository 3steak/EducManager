import { Injectable } from '@angular/core';
import { delay, Observable, of } from 'rxjs';

import { Course } from '../../../shared/models/course.model';
import { MOCK_COURSES } from '../mocks/courses.mock';

@Injectable({
  providedIn: 'root',
})
export class CourseService {
  getAll(): Observable<Course[]> {
    return of([...MOCK_COURSES]).pipe(delay(400));
  }
}
