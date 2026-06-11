export interface Course {
  id?: number;
  code: string;
  titre: string;
  description?: string;
  dureeJours?: number;
  dureeHeures?: number;
  formateur?: string;
  etudiants?: number;
}
