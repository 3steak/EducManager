import { UserRole } from './user-role.model';

export interface User {
  id?: number;
  email: string;
  nom?: string;
  prenom?: string;
  roles: UserRole[];
}
