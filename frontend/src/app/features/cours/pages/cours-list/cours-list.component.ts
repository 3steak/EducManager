import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { CourseCardComponent } from '../../../../shared/components/course-card/course-card.component';
import { Course } from '../../../../shared/models/course.model';
import { CourseService } from '../../services/course.service';

@Component({
  selector: 'app-cours-list',
  imports: [CommonModule, FormsModule, CourseCardComponent],
  templateUrl: './cours-list.component.html',
  styleUrl: './cours-list.component.scss',
})
export class CoursListComponent implements OnInit {
  courses: Course[] = [];
  loading = true;
  error: string | null = null;
  searchQuery = '';

  constructor(private courseService: CourseService) {}

  get filteredCourses(): Course[] {
    const query = this.searchQuery.trim().toLowerCase();
    if (!query) {
      return this.courses;
    }
    return this.courses.filter(
      (c) =>
        c.titre.toLowerCase().includes(query) ||
        c.code.toLowerCase().includes(query),
    );
  }

  ngOnInit(): void {
    this.courseService.getAll().subscribe({
      next: (data) => {
        this.courses = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Impossible de charger les cours.';
        this.loading = false;
      },
    });
  }
}
