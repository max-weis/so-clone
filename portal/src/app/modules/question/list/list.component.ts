import {Component, OnInit} from '@angular/core';
import {Question} from "../../../shared/models/Question";
import {QuestionService} from "../../../core/services/question.service";

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {
  questions: Question[];

  constructor(private qs: QuestionService) {
  }

  ngOnInit(): void {
    this.qs.listQuestions().subscribe(res => this.questions = res);
  }
}
