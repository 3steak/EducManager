import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { environment } from '../../../../environments/environment';
import { Cursus } from '../../../shared/models/cursus.model';

@Injectable({
  providedIn: 'root',
})
export class CursusService {
  private url = environment.apiUrl + '/cursus';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Cursus[]> {
    return this.http.get<Cursus[]>(this.url);
  }

  getById(id: number): Observable<Cursus> {
    return this.http.get<Cursus>(`${this.url}/${id}`);
  }

  create(cursus: Cursus): Observable<Cursus> {
    return this.http.post<Cursus>(this.url, cursus);
  }

  update(id: number, cursus: Cursus): Observable<Cursus> {
    return this.http.put<Cursus>(`${this.url}/${id}`, cursus);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
