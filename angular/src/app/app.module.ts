import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatSortModule} from "@angular/material/sort";
import {MatTableModule} from "@angular/material/table";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatDialogModule} from "@angular/material/dialog";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatSelectModule} from "@angular/material/select";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";
import {ErrorDialogComponent} from "./error-dialog/error-dialog.component";
import {LoginComponent} from "./login/login.component";
import {AuthInterceptor} from "./_service/auth.interceptor";
import {TeachersComponent} from "./teachers/teachers.component";
import {StudentGroupsComponent} from "./student-groups/student-groups.component";
import {LoadComponent} from "./load/load.component";
import {UsersComponent} from "./users/users.component";
import {RegisterComponent} from "./register/register.component";
import {TeacherEditDialogComponent} from "./teachers/teacher-edit-dialog/teacher-edit-dialog.component";
import {StudentGroupEditDialogComponent} from "./student-groups/student-group-edit-dialog/student-group-edit-dialog.component";
import {LoadEditDialogComponent} from "./load/load-edit-dialog/load-edit-dialog.component";
import {UserEditDialogComponent} from "./users/user-edit-dialog/user-edit-dialog.component";

@NgModule({
  declarations: [
    AppComponent,
    ErrorDialogComponent,
    LoginComponent,

    RegisterComponent,
    TeachersComponent,
    StudentGroupsComponent,
    LoadComponent,
    UsersComponent,

    TeacherEditDialogComponent,
    StudentGroupEditDialogComponent,
    LoadEditDialogComponent,
    UserEditDialogComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        MatPaginatorModule,
        MatSortModule,
        MatTableModule,
        MatProgressSpinnerModule,
        HttpClientModule,
        MatIconModule,
        MatButtonModule,
        MatFormFieldModule,
        MatInputModule,
        MatDialogModule,
        FormsModule,
        MatCheckboxModule,
        MatSelectModule,
        MatDatepickerModule,
        MatNativeDateModule,
        ReactiveFormsModule
    ],
  providers: [
    MatDatepickerModule,
    MatNativeDateModule,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi:true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
