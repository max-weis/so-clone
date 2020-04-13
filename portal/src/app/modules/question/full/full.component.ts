import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {QuestionService} from "../../../core/services/question.service";
import {Question} from "../../../shared/models/Question";

@Component({
  selector: 'app-full',
  templateUrl: './full.component.html',
  styleUrls: ['./full.component.scss']
})
export class FullComponent implements OnInit {

  question: Question;

  constructor(private route: ActivatedRoute, private qs: QuestionService) {
  }

  ngOnInit(): void {
    let id = this.route.snapshot.params["id"];
    this.qs.getQuestion(id).subscribe(res => this.question = res);
  }

}
