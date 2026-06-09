import { Component, computed, inject } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

import { NAV_ITEMS } from '../../../app-routing/nav-items';
import { ROUTE_PATHS } from '../../../app-routing/route-paths';
import { AuthService } from '../../../core/auth/auth.service';

@Component({
  selector: 'app-main-layout',
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './main-layout.component.html',
  styleUrl: './main-layout.component.scss',
})
export class MainLayoutComponent {
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  readonly routes = ROUTE_PATHS;
  readonly currentUser = computed(() => this.authService.getCurrentUser());
  readonly visibleNavItems = computed(() =>
    NAV_ITEMS.filter(
      (item) => !item.roles || this.authService.hasAnyRole(item.roles),
    ),
  );

  logout(): void {
    this.authService.logout();
    this.router.navigate([ROUTE_PATHS.login]);
  }
}
