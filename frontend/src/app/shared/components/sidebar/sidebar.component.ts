import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';

import { AuthService } from '../../../core/auth/auth.service';
import { NAV_ITEMS, NavItem } from '../../config/nav.config';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-sidebar',
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss',
})
export class SidebarComponent {
  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  get user(): User | null {
    return this.authService.getUser();
  }

  get visibleItems(): NavItem[] {
    const user = this.user;
    if (!user) {
      return [];
    }
    return NAV_ITEMS.filter((item) => item.roles.includes(user.role));
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
