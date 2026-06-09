import { Routes } from '@angular/router';

import { authGuard } from '../core/auth/auth.guard';
import { guestGuard } from '../core/auth/guest.guard';
import { MainLayoutComponent } from '../shared/components/main-layout/main-layout.component';
import { ROUTE_PATHS } from './route-paths';

export const routes: Routes = [
  {
    path: ROUTE_PATHS.login,
    canActivate: [guestGuard],
    loadComponent: () =>
      import('../features/auth/pages/login/login.component').then((m) => m.LoginComponent),
  },
  {
    path: ROUTE_PATHS.home,
    component: MainLayoutComponent,
    canActivate: [authGuard],
    children: [
      {
        path: ROUTE_PATHS.home,
        loadComponent: () =>
          import('../features/home/home.component').then((m) => m.HomeComponent),
      },
      { path: '**', redirectTo: ROUTE_PATHS.home },
    ],
  },
  { path: '**', redirectTo: ROUTE_PATHS.login },
];
