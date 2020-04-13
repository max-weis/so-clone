import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Question} from "../../shared/models/Question";
import {Observable} from "rxjs";
import {retry} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  private api = 'http://localhost:8080/question'

  constructor(private http: HttpClient) {
  }

  listQuestions(): Observable<Question[]> {
    return this.http.get<Question[]>(`${this.api}?limit=5&offset=0`).pipe(
      retry(3)
    );
  }
}
