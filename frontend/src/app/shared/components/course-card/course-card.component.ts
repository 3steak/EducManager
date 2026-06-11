import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

import { Course } from '../../models/course.model';

@Component({
  selector: 'app-course-card',
  imports: [CommonModule],
  templateUrl: './course-card.component.html',
  styleUrl: './course-card.component.scss',
})
export class CourseCardComponent {
  @Input({ required: true }) course!: Course;

  get formateurName(): string {
    return this.course.formateur ?? '—';
  }

  get studentCount(): number {
    return this.course.etudiants ?? 0;
  }

  get dureeLabel(): string {
    if (this.course.dureeHeures) {
      return `${this.course.dureeHeures}h`;
    }
    if (this.course.dureeJours) {
      return `${this.course.dureeJours * 8}h`;
    }
    return '—';
  }
}
