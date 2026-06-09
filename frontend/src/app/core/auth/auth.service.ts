import { Injectable, signal } from '@angular/core';

import { User } from '../../shared/models/user.model';
import { UserRole } from '../../shared/models/user-role.model';

const TOKEN_KEY = 'educmanager_token';
const USER_KEY = 'educmanager_user';

interface DemoAccount {
  email: string;
  password: string;
  user: User;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly demoAccounts: DemoAccount[] = [
    {
      email: 'admin@educmanager.fr',
      password: 'admin123',
      user: {
        email: 'admin@educmanager.fr',
        nom: 'Dupont',
        prenom: 'Alice',
        roles: [UserRole.ADMIN],
      },
    },
    {
      email: 'formateur@educmanager.fr',
      password: 'formateur123',
      user: {
        email: 'formateur@educmanager.fr',
        nom: 'Martin',
        prenom: 'Bob',
        roles: [UserRole.FORMATEUR],
      },
    },
    {
      email: 'referente@educmanager.fr',
      password: 'referente123',
      user: {
        email: 'referente@educmanager.fr',
        nom: 'Dupont',
        prenom: 'Alice',
        roles: [UserRole.REFERENTE],
      },
    },
    {
      email: 'etudiant@educmanager.fr',
      password: 'etudiant123',
      user: {
        email: 'etudiant@educmanager.fr',
        nom: 'Bernard',
        prenom: 'Claire',
        roles: [UserRole.ETUDIANT],
      },
    },
  ];

  private readonly currentUser = signal<User | null>(null);

  constructor() {
    this.restoreSession();
  }

  /**
   * TODO: remplacer par un appel HTTP vers l'API backend (/api/auth/login).
   */
  login(email: string, password: string): boolean {
    const account = this.demoAccounts.find(
      (entry) => entry.email === email && entry.password === password,
    );

    if (!account) {
      return false;
    }

    const token = `mock-token-${account.user.email}`;
    localStorage.setItem(TOKEN_KEY, token);
    localStorage.setItem(USER_KEY, JSON.stringify(account.user));
    this.currentUser.set(account.user);
    return true;
  }

  logout(): void {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
    this.currentUser.set(null);
  }

  getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  isAuthenticated(): boolean {
    return !!this.getToken() && !!this.currentUser();
  }

  getCurrentUser(): User | null {
    return this.currentUser();
  }

  getUserRoles(): UserRole[] {
    return this.currentUser()?.roles ?? [];
  }

  hasAnyRole(roles: UserRole[]): boolean {
    const userRoles = this.getUserRoles();
    return roles.some((role) => userRoles.includes(role));
  }

  hasRole(role: UserRole): boolean {
    return this.getUserRoles().includes(role);
  }

  private restoreSession(): void {
    const token = this.getToken();
    const storedUser = localStorage.getItem(USER_KEY);

    if (!token || !storedUser) {
      return;
    }

    try {
      this.currentUser.set(JSON.parse(storedUser) as User);
    } catch {
      this.logout();
    }
  }
}
