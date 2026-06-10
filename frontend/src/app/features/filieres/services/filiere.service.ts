import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { environment } from '../../../../environments/environment';
import { Filiere } from '../../../shared/models/filiere.model';

// service injectable dans les composants
@Injectable({
  providedIn: 'root',
})
export class FiliereService {
  private url = environment.apiUrl + '/filieres';

  // injection http dans le service
  constructor(private http: HttpClient) { }


  // Observable = promise, conteneur qui attend la reponse
  getAll(): Observable<Filiere[]> {
    // this.url = /api/filieres
    return this.http.get<Filiere[]>(this.url);
  }

  create(filiere: Filiere): Observable<Filiere> {
    return this.http.post<Filiere>(this.url, filiere);
  }
}
