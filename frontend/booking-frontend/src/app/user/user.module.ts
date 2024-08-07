import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserRoutingModule } from './user-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { UserPanelComponent } from './components/user-panel/user-panel.component';
import { EventListComponent } from './components/user-panel/event-list/event-list.component';
import { EventComponent } from './components/user-panel/event/event.component';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    RegisterComponent,
    LoginComponent,
    UserPanelComponent,
    EventListComponent,
    EventComponent
  ],
  imports: [
    FormsModule,
    HttpClientModule,
    CommonModule,
    UserRoutingModule,
    ReactiveFormsModule
  ],
  exports: [
  ]
})
export class UserModule { }
