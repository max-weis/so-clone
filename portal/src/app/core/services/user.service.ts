import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {retry} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private api = 'http://localhost:8090/auth/realms/master/protocol/openid-connect/token'

  constructor(private http: HttpClient) {
  }

  getToken(username: string, password: string): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'x-www-form-urlencoded',
        "grant_type": "password",
        "client_id": "portal-ui",
        "username": username,
        "password": password
      })
    };

    return this.http.post<any>(this.api, httpOptions).pipe(
      retry(3)
    );
  }
}
