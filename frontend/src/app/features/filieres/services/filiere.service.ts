import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { environment } from '../../../../environments/environment';
import { Filiere } from '../../../shared/models/filiere.model';

@Injectable({
  providedIn: 'root',
})
export class FiliereService {
  private url = environment.apiUrl + '/filieres';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Filiere[]> {
    return this.http.get<Filiere[]>(this.url);
  }

  create(filiere: Filiere): Observable<Filiere> {
    return this.http.post<Filiere>(this.url, filiere);
  }
}
