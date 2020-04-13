import {Component, OnInit} from '@angular/core';
import {UserService} from "../../core/services/user.service";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  token: any;

  constructor(private us: UserService) {
  }

  ngOnInit(): void {
  }

  login(form: NgForm) {

    console.log(form.value);
    this.us.getToken(form.value.username, form.value.password).subscribe(res => this.token = res);

    console.log(this.token)
  }

}
