import { Routes } from '@angular/router';

import { authGuard } from '../core/auth/auth.guard';
import { defaultRouteGuard } from '../core/auth/default-route.guard';
import { guestGuard } from '../core/auth/guest.guard';
import { roleGuard } from '../core/auth/role.guard';
import { LoginComponent } from '../features/auth/pages/login/login.component';
import { CalendrierComponent } from '../features/calendrier/pages/calendrier/calendrier.component';
import { CoursListComponent } from '../features/cours/pages/cours-list/cours-list.component';
import { DashboardComponent } from '../features/dashboard/dashboard.component';
import { MainLayoutComponent } from '../shared/components/main-layout/main-layout.component';
import { PlaceholderPageComponent } from '../shared/components/placeholder-page/placeholder-page.component';

const placeholder = (title: string) => ({
  component: PlaceholderPageComponent,
  data: { title },
});

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
      { path: '', canActivate: [defaultRouteGuard], children: [] },
      {
        path: 'dashboard',
        component: DashboardComponent,
        canActivate: [roleGuard('ADMIN', 'FORMATEUR', 'REFERENTE', 'ETUDIANT')],
      },
      {
        path: 'cours',
        component: CoursListComponent,
        canActivate: [roleGuard('ADMIN', 'FORMATEUR')],
      },
      {
        path: 'promotions',
        ...placeholder('Promotions'),
        canActivate: [roleGuard('ADMIN', 'REFERENTE')],
      },
      {
        path: 'calendrier',
        component: CalendrierComponent,
        canActivate: [roleGuard('ETUDIANT')],
      },
      {
        path: 'etudiants',
        ...placeholder('Étudiants'),
        canActivate: [roleGuard('ADMIN', 'REFERENTE')],
      },
      {
        path: 'filieres',
        ...placeholder('Filières'),
        canActivate: [roleGuard('ADMIN', 'REFERENTE')],
      },
      {
        path: 'cursus',
        ...placeholder('Cursus'),
        canActivate: [roleGuard('ADMIN', 'REFERENTE')],
      },
      {
        path: 'inscriptions',
        ...placeholder('Inscriptions'),
        canActivate: [roleGuard('ADMIN', 'REFERENTE')],
      },
      {
        path: 'utilisateurs',
        ...placeholder('Utilisateurs'),
        canActivate: [roleGuard('ADMIN')],
      },
    ],
  },
  { path: '**', redirectTo: 'login' },
];
