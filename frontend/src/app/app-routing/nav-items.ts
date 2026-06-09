import { UserRole } from '../shared/models/user-role.model';
import { ROUTE_PATHS } from './route-paths';

export interface NavItem {
  label: string;
  path: string;
  roles?: UserRole[];
}

/**
 * Liens de navigation affiches selon le role de l'utilisateur.
 * Ajouter ici les modules au fur et a mesure de leur implementation.
 */
export const NAV_ITEMS: NavItem[] = [
  { label: 'Accueil', path: ROUTE_PATHS.home },
  // Exemples a activer quand les pages seront implementees :
  // { label: 'Promotions', path: ROUTE_PATHS.promotions },
  // { label: 'Calendrier', path: ROUTE_PATHS.calendrier },
  // { label: 'Filieres', path: ROUTE_PATHS.filieres},
  // { label: 'Cursus', path: ROUTE_PATHS.cursus },
  // { label: 'Cours', path: ROUTE_PATHS.cours },
  // { label: 'Inscriptions', path: ROUTE_PATHS.inscriptions },
];
