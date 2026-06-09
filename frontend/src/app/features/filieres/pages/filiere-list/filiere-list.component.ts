import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { FiliereService } from '../../services/filiere.service';
import { Filiere } from '../../../../shared/models/filiere.model';

@Component({
  selector: 'app-filiere-list',
  imports: [CommonModule, FormsModule],
  templateUrl: './filiere-list.component.html',
  styleUrl: './filiere-list.component.scss',
})
export class FiliereListComponent implements OnInit {
  filieres: Filiere[] = [];
  name = '';
  loading = true;
  error: string | null = null;

  constructor(private filiereService: FiliereService) {}

  ngOnInit(): void {
    this.loadFilieres();
  }

  loadFilieres(): void {
    this.loading = true;
    this.error = null;

    this.filiereService.getAll().subscribe({
      next: (data) => {
        this.filieres = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Impossible de charger les filières.';
        this.loading = false;
      },
    });
  }

  onSubmit(): void {
    const name = this.name.trim();
    if (!name) {
      return;
    }

    this.error = null;

    this.filiereService.create({ name }).subscribe({
      next: () => {
        this.name = '';
        this.loadFilieres();
      },
      error: () => {
        this.error = 'Impossible de créer la filière.';
      },
    });
  }
}
