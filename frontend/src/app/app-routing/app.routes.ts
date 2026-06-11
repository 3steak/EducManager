import { Routes } from '@angular/router';

import { authGuard } from '../core/auth/auth.guard';
import { defaultRouteGuard } from '../core/auth/default-route.guard';
import { guestGuard } from '../core/auth/guest.guard';
import { roleGuard } from '../core/auth/role.guard';
import { LoginComponent } from '../features/auth/pages/login/login.component';
import { CalendrierComponent } from '../features/calendrier/pages/calendrier/calendrier.component';
import { CoursListComponent } from '../features/cours/pages/cours-list/cours-list.component';
import { DashboardComponent } from '../features/dashboard/dashboard.component';
import { CursusListComponent } from '../features/cursus/pages/cursus-list/cursus-list.component';
import { FiliereListComponent } from '../features/filieres/pages/filiere-list/filiere-list.component';
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
        canActivate: [roleGuard('FORMATEUR', 'REFERENTE', 'ETUDIANT')],
      },
      {
        path: 'cours',
        component: CoursListComponent,
        canActivate: [roleGuard('REFERENTE', 'FORMATEUR')],
      },
      {
        path: 'promotions',
        ...placeholder('Promotions'),
        canActivate: [roleGuard('REFERENTE')],
      },
      {
        path: 'calendrier',
        component: CalendrierComponent,
        canActivate: [roleGuard('ETUDIANT')],
      },
      {
        path: 'filieres',
        component: FiliereListComponent,
        canActivate: [roleGuard('REFERENTE')],
      },
      {
        path: 'cursus',
        component: CursusListComponent,
        canActivate: [roleGuard('REFERENTE')],
      },
      {
        path: 'inscriptions',
        ...placeholder('Inscriptions'),
        canActivate: [roleGuard('REFERENTE')],
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
