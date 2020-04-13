import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NavigationComponent} from './core/navigation/navigation.component';
import {HomeComponent} from './modules/home/home.component';
import {SecondNavComponent} from './core/second-nav/second-nav.component';
import {LoginComponent} from './modules/login/login.component';
import {RegisterComponent} from './modules/register/register.component';
import {ListComponent} from './modules/question/list/list.component';
import {ItemComponent} from './modules/question/item/item.component';
import {HttpClientModule} from '@angular/common/http';
import { FullComponent } from './modules/question/full/full.component';
import { AnswerComponent } from './modules/answer/answer.component';
import { CommentComponent } from './modules/comment/comment.component';
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    HomeComponent,
    SecondNavComponent,
    LoginComponent,
    RegisterComponent,
    ListComponent,
    ItemComponent,
    FullComponent,
    AnswerComponent,
    CommentComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
