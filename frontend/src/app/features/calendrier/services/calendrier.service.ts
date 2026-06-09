import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

import { ApiService } from '../../../core/services/api.service';
import { CalendrierEvent } from '../../../shared/models/calendrier-event.model';

@Injectable({
  providedIn: 'root',
})
export class CalendrierService {
  private readonly resourcePath = '/calendrier';

  constructor(private readonly api: ApiService) {}

  /**
   * TODO: connecter a l'endpoint backend du calendrier.
   */
  getAll(): Observable<CalendrierEvent[]> {
    return of([]);
  }
}
