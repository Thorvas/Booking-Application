import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserRoutingModule } from './user-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { RegisterComponent } from './components/register/register.component';


@NgModule({
  declarations: [
    RegisterComponent
  ],
  imports: [
    FormsModule,
    HttpClientModule,
    CommonModule,
    UserRoutingModule
  ],
  exports: [
    RegisterComponent
  ]
})
export class UserModule { }
