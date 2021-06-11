import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {TeachersComponent} from "./teachers/teachers.component";
import {StudentGroupsComponent} from "./student-groups/student-groups.component";
import {LoginComponent} from "./login/login.component";
import {LoadComponent} from "./load/load.component";
import {UsersComponent} from "./users/users.component";
import {RegisterComponent} from "./register/register.component";

const routes: Routes = [
  {path: 'teachers', component: TeachersComponent},
  {path: 'student-groups', component: StudentGroupsComponent},
  {path: 'load', component: LoadComponent},
  {path: 'users', component: UsersComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},

  {path: '**', redirectTo: '/login'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
