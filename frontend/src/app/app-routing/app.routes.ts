import { Routes } from '@angular/router';

import { authGuard } from '../core/auth/auth.guard';
import { guestGuard } from '../core/auth/guest.guard';
import { LoginComponent } from '../features/auth/pages/login/login.component';
import { CursusListComponent } from '../features/cursus/pages/cursus-list/cursus-list.component';
import { FiliereListComponent } from '../features/filieres/pages/filiere-list/filiere-list.component';
import { HomeComponent } from '../features/home/home.component';
import { MainLayoutComponent } from '../shared/components/main-layout/main-layout.component';

export const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [guestGuard],
  },
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: '', component: HomeComponent },
      { path: 'filieres', component: FiliereListComponent },
      { path: 'cursus', component: CursusListComponent },
    ],
  },
  { path: '**', redirectTo: 'login' },
];
