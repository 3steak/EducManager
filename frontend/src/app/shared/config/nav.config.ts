import { UserRole } from '../models/user-role.model';

export interface NavItem {
  label: string;
  path: string;
  roles: UserRole[];
}

export const NAV_ITEMS: NavItem[] = [
  { label: 'Tableau de bord', path: '/dashboard', roles: ['ADMIN', 'FORMATEUR', 'REFERENTE', 'ETUDIANT'] },
  { label: 'Promotions', path: '/promotions', roles: ['ADMIN', 'REFERENTE'] },
  { label: 'Calendrier', path: '/calendrier', roles: ['ETUDIANT'] },
  { label: 'Étudiants', path: '/etudiants', roles: ['ADMIN', 'REFERENTE'] },
  { label: 'Filières', path: '/filieres', roles: ['ADMIN', 'REFERENTE'] },
  { label: 'Cursus', path: '/cursus', roles: ['ADMIN', 'REFERENTE'] },
  { label: 'Cours', path: '/cours', roles: ['ADMIN', 'FORMATEUR'] },
  { label: 'Inscriptions', path: '/inscriptions', roles: ['ADMIN', 'REFERENTE'] },
  { label: 'Utilisateurs', path: '/utilisateurs', roles: ['ADMIN'] },
];
