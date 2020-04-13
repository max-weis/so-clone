import {Component, Input, OnInit} from '@angular/core';
import {Question} from "../../../shared/models/Question";
import {Router} from "@angular/router";

@Component({
  selector: 'app-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.scss']
})
export class ItemComponent implements OnInit {

  @Input() question: Question;

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  routeToFull() {
    this.router.navigateByUrl('/question/' + this.question.id);
  }
}
