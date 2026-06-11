import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';

import { CalendrierEvent } from '../../../../shared/models/calendrier-event.model';
import { CalendrierService } from '../../services/calendrier.service';

interface JourGroupe {
  dateLabel: string;
  events: CalendrierEvent[];
}

@Component({
  selector: 'app-calendrier',
  imports: [CommonModule],
  templateUrl: './calendrier.component.html',
  styleUrl: './calendrier.component.scss',
})
export class CalendrierComponent implements OnInit {
  events: CalendrierEvent[] = [];
  loading = true;

  constructor(private calendrierService: CalendrierService) {}

  get eventsParJour(): JourGroupe[] {
    const map = new Map<string, CalendrierEvent[]>();

    for (const event of this.events) {
      const list = map.get(event.date) ?? [];
      list.push(event);
      map.set(event.date, list);
    }

    return Array.from(map.entries())
      .sort(([a], [b]) => a.localeCompare(b))
      .map(([date, events]) => ({
        dateLabel: this.formatDate(date),
        events,
      }));
  }

  ngOnInit(): void {
    this.calendrierService.getAll().subscribe({
      next: (data) => {
        this.events = data;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      },
    });
  }

  private formatDate(isoDate: string): string {
    const date = new Date(isoDate + 'T12:00:00');
    return date.toLocaleDateString('fr-FR', {
      weekday: 'long',
      day: 'numeric',
      month: 'long',
      year: 'numeric',
    });
  }
}
