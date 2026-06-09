import { Injectable } from '@angular/core';

import { User } from '../../shared/models/user.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private currentUser: User | null = null;

  // comptes de test en dur (a remplacer par l'API plus tard)
  private accounts = [
    {
      email: 'admin@educmanager.fr',
      password: 'admin123',
      user: { email: 'admin@educmanager.fr', nom: 'Dupont', prenom: 'Alice', role: 'ADMIN' },
    },
    {
      email: 'formateur@educmanager.fr',
      password: 'formateur123',
      user: { email: 'formateur@educmanager.fr', nom: 'Martin', prenom: 'Bob', role: 'FORMATEUR' },
    },
    {
      email: 'referente@educmanager.fr',
      password: 'referente123',
      user: { email: 'referente@educmanager.fr', nom: 'Leroy', prenom: 'Sophie', role: 'REFERENTE' },
    },
    {
      email: 'etudiant@educmanager.fr',
      password: 'etudiant123',
      user: { email: 'etudiant@educmanager.fr', nom: 'Bernard', prenom: 'Claire', role: 'ETUDIANT' },
    },
  ];

  constructor() {
    const saved = localStorage.getItem('user');
    if (saved) {
      this.currentUser = JSON.parse(saved);
    }
  }

  login(email: string, password: string): boolean {
    const account = this.accounts.find((a) => a.email === email && a.password === password);
    if (!account) {
      return false;
    }
    this.currentUser = account.user;
    localStorage.setItem('user', JSON.stringify(account.user));
    return true;
  }

  logout(): void {
    this.currentUser = null;
    localStorage.removeItem('user');
  }

  isLoggedIn(): boolean {
    return this.currentUser !== null;
  }

  getUser(): User | null {
    return this.currentUser;
  }
}
