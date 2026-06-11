import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { FiliereService } from '../../../filieres/services/filiere.service';
import { Cursus } from '../../../../shared/models/cursus.model';
import { Filiere } from '../../../../shared/models/filiere.model';
import { CursusService } from '../../services/cursus.service';

@Component({
  selector: 'app-cursus-list',
  imports: [CommonModule, FormsModule],
  templateUrl: './cursus-list.component.html',
  styleUrl: './cursus-list.component.scss',
})
export class CursusListComponent implements OnInit {
  cursus: Cursus[] = [];
  filieres: Filiere[] = [];
  name = '';
  filiereId: number | null = null;
  editingCursusId: number | null = null;
  editingName = '';
  editingFiliereId: number | null = null;
  loading = true;
  error: string | null = null;

  constructor(
    private cursusService: CursusService,
    private filiereService: FiliereService,
  ) {}

  ngOnInit(): void {
    this.loadFilieres();
    this.loadCursus();
  }

  loadCursus(): void {
    this.loading = true;
    this.error = null;

    this.cursusService.getAll().subscribe({
      next: (data) => {
        this.cursus = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Impossible de charger les cursus.';
        this.loading = false;
      },
    });
  }

  loadFilieres(): void {
    this.filiereService.getAll().subscribe({
      next: (data) => {
        this.filieres = data;
      },
      error: () => {
        this.error = 'Impossible de charger les filieres.';
      },
    });
  }

  onSubmit(): void {
    const name = this.name.trim();
    if (!name || this.filiereId === null) {
      return;
    }

    this.error = null;

    this.cursusService.create({ name, filiereId: this.filiereId }).subscribe({
      next: () => {
        this.name = '';
        this.filiereId = null;
        this.loadCursus();
      },
      error: () => {
        this.error = 'Impossible de creer le cursus.';
      },
    });
  }

  startEdit(cursus: Cursus): void {
    if (cursus.id === undefined) {
      return;
    }

    this.editingCursusId = cursus.id;
    this.editingName = cursus.name;
    this.editingFiliereId = cursus.filiereId;
  }

  cancelEdit(): void {
    this.editingCursusId = null;
    this.editingName = '';
    this.editingFiliereId = null;
  }

  updateCursus(): void {
    if (this.editingCursusId === null) {
      return;
    }

    const name = this.editingName.trim();
    if (!name || this.editingFiliereId === null) {
      return;
    }

    this.error = null;

    this.cursusService.update(this.editingCursusId, {
      name,
      filiereId: this.editingFiliereId,
    }).subscribe({
      next: () => {
        this.cancelEdit();
        this.loadCursus();
      },
      error: () => {
        this.error = 'Impossible de modifier le cursus.';
      },
    });
  }

  deleteCursus(id: number): void {
    if (!confirm('Supprimer ce cursus ?')) {
      return;
    }

    this.error = null;

    this.cursusService.delete(id).subscribe({
      next: () => {
        if (this.editingCursusId === id) {
          this.cancelEdit();
        }
        this.loadCursus();
      },
      error: () => {
        this.error = 'Impossible de supprimer le cursus.';
      },
    });
  }
}
