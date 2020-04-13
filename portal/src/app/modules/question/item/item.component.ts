import {Component, Input, OnInit} from '@angular/core';
import {Question} from "../../../shared/models/Question";

@Component({
  selector: 'app-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.scss']
})
export class ItemComponent implements OnInit {

  @Input() question: Question;

  constructor() { }

  ngOnInit(): void {
  }

}
