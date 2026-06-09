import { Component, inject, OnInit, signal } from '@angular/core';

import { CourseService } from '../cours/services/course.service';
import { Course } from '../../shared/models/course.model';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent implements OnInit {
  private readonly courseService = inject(CourseService);

  readonly courses = signal<Course[]>([]);
  readonly loading = signal(true);
  readonly error = signal<string | null>(null);

  ngOnInit(): void {
    this.courseService.getAll().subscribe({
      next: (courses) => {
        this.courses.set(courses);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Impossible de joindre le backend. Verifiez que Spring Boot tourne sur le port 8080.');
        this.loading.set(false);
      },
    });
  }
}
