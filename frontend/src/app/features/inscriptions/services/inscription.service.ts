import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

import { ApiService } from '../../../core/services/api.service';
import { Inscription } from '../../../shared/models/inscription.model';

@Injectable({
  providedIn: 'root',
})
export class InscriptionService {
  private readonly resourcePath = '/inscriptions';

  constructor(private readonly api: ApiService) {}

  /**
   * TODO: connecter a l'endpoint backend des inscriptions.
   */
  getAll(): Observable<Inscription[]> {
    return of([]);
  }
}
