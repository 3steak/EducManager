import { UserRole } from '../models/user-role.model';

export interface NavItem {
  label: string;
  path: string;
  roles: UserRole[];
}

export const NAV_ITEMS: NavItem[] = [
  { label: 'Tableau de bord', path: '/dashboard', roles: ['FORMATEUR', 'REFERENTE', 'ETUDIANT'] },
  { label: 'Promotions', path: '/promotions', roles: ['REFERENTE'] },
  { label: 'Calendrier', path: '/calendrier', roles: ['ETUDIANT'] },
  { label: 'Filières', path: '/filieres', roles: ['REFERENTE'] },
  { label: 'Cursus', path: '/cursus', roles: ['REFERENTE'] },
  { label: 'Cours', path: '/cours', roles: ['REFERENTE', 'FORMATEUR'] },
  { label: 'Inscriptions', path: '/inscriptions', roles: ['REFERENTE'] },
  { label: 'Utilisateurs', path: '/utilisateurs', roles: ['ADMIN'] },
];
