import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

import { ApiService } from '../../../core/services/api.service';
import { Promotion } from '../../../shared/models/promotion.model';

@Injectable({
  providedIn: 'root',
})
export class PromotionService {
  private readonly resourcePath = '/promotions';

  constructor(private readonly api: ApiService) {}

  /**
   * TODO: connecter a l'endpoint backend des promotions.
   */
  getAll(): Observable<Promotion[]> {
    return of([]);
  }
}
