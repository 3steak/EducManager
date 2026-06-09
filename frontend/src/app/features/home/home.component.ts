import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';

import { CourseService } from '../cours/services/course.service';
import { Course } from '../../shared/models/course.model';

@Component({
  selector: 'app-home',
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent implements OnInit {
  courses: Course[] = [];
  loading = true;
  error: string | null = null;

  constructor(private courseService: CourseService) {}

  ngOnInit(): void {
    this.courseService.getAll().subscribe({
      next: (data) => {
        this.courses = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Impossible de joindre le backend. Verifiez que Spring Boot tourne sur le port 8080.';
        this.loading = false;
      },
    });
  }
}
