import { Injectable } from '@angular/core';
import { delay, Observable, of } from 'rxjs';

import { CalendrierEvent } from '../../../shared/models/calendrier-event.model';
import { MOCK_CALENDRIER } from '../mocks/calendrier.mock';

@Injectable({
  providedIn: 'root',
})
export class CalendrierService {
  getAll(): Observable<CalendrierEvent[]> {
    return of([...MOCK_CALENDRIER]).pipe(delay(300));
  }
}
