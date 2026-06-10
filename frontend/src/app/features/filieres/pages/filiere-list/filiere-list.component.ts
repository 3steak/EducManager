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
  editingFiliereId: number | null = null;
  editingName = '';
  loading = true;
  error: string | null = null;

  // injection du service
  constructor(private filiereService: FiliereService) { }

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

  startEdit(filiere: Filiere): void {
    if (filiere.id === undefined) {
      return;
    }

    this.editingFiliereId = filiere.id;
    this.editingName = filiere.name;
  }

  cancelEdit(): void {
    this.editingFiliereId = null;
    this.editingName = '';
  }

  updateFiliere(): void {
    if (this.editingFiliereId === null) {
      return;
    }

    const name = this.editingName.trim();
    if (!name) {
      return;
    }

    this.error = null;

    this.filiereService.update(this.editingFiliereId, { name }).subscribe({
      next: () => {
        this.cancelEdit();
        this.loadFilieres();
      },
      error: () => {
        this.error = 'Impossible de modifier la filière.';
      },
    });
  }

  deleteFiliere(id: number): void {
    if (!confirm('Supprimer cette filière ?')) {
      return;
    }

    this.error = null;

    this.filiereService.delete(id).subscribe({
      next: () => {
        if (this.editingFiliereId === id) {
          this.cancelEdit();
        }
        this.loadFilieres();
      },
      error: () => {
        this.error = 'Impossible de supprimer la filière.';
      },
    });
  }
}
