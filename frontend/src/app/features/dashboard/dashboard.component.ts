import { Component } from '@angular/core';

import { AuthService } from '../../core/auth/auth.service';

@Component({
  selector: 'app-dashboard',
  template: `
    <div class="dashboard">
      <h1>EducManager</h1>
      <p>Tableau de bord — Bienvenue, {{ userName }}.</p>
      <p class="wip">Contenu à compléter.</p>
    </div>
  `,
  styles: `
    .dashboard {
      padding: 32px 40px;
    }
    h1 {
      margin: 0 0 12px;
      font-size: 24px;
      font-weight: 700;
      color: var(--color-text);
    }
    p {
      color: #555;
      font-size: 14px;
    }
    .wip {
      color: #888;
      font-style: italic;
    }
  `,
})
export class DashboardComponent {
  constructor(private authService: AuthService) {}

  get userName(): string {
    const user = this.authService.getUser();
    return user ? `${user.prenom} ${user.nom}` : '';
  }
}
