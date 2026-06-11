import { Course } from '../../../shared/models/course.model';

export const MOCK_COURSES: Course[] = [
  {
    id: 1,
    code: 'WEB-101',
    titre: 'Fondamentaux HTML & CSS',
    dureeHeures: 40,
    formateur: 'Jean Dupont',
    etudiants: 24,
  },
  {
    id: 2,
    code: 'JS-101',
    titre: 'Bases de JavaScript',
    dureeHeures: 35,
    formateur: 'Marie Curie',
    etudiants: 18,
  },
  {
    id: 3,
    code: 'REACT-201',
    titre: 'Framework React',
    dureeHeures: 50,
    formateur: 'Jean Dupont',
    etudiants: 15,
  },
  {
    id: 4,
    code: 'NODE-201',
    titre: 'Backend Node.js',
    dureeHeures: 45,
    formateur: 'Pierre Lambert',
    etudiants: 12,
  },
];
