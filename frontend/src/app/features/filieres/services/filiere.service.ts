import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

import { ApiService } from '../../../core/services/api.service';
import { Filiere } from '../../../shared/models/filiere.model';

@Injectable({
  providedIn: 'root',
})
export class FiliereService {
  private readonly resourcePath = '/filieres';

  constructor(private readonly api: ApiService) {}

  /**
   * TODO: connecter a l'endpoint backend des filieres.
   */
  getAll(): Observable<Filiere[]> {
    return of([]);
  }
}
