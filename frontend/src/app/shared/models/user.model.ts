import { UserRole } from './user-role.model';

export interface User {
  email: string;
  nom: string;
  prenom: string;
  role: UserRole;
}
