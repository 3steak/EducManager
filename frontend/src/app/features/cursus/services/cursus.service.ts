import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

import { ApiService } from '../../../core/services/api.service';
import { Cursus } from '../../../shared/models/cursus.model';

@Injectable({
  providedIn: 'root',
})
export class CursusService {
  private readonly resourcePath = '/cursus';

  constructor(private readonly api: ApiService) {}

  /**
   * TODO: connecter a l'endpoint backend des cursus.
   */
  getAll(): Observable<Cursus[]> {
    return of([]);
  }
}
