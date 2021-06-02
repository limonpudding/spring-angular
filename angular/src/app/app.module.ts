import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {RoomComponent} from './room/room.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatSortModule} from "@angular/material/sort";
import {MatTableModule} from "@angular/material/table";
import {MatPaginatorModule} from "@angular/material/paginator";
import {InstrumentComponent} from './instrument/instrument.component';
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {RoomEditDialogComponent} from './room/room-edit-dialog/room-edit-dialog.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatDialogModule} from "@angular/material/dialog";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {AddInstrumentDialogComponent} from './room/room-edit-dialog/add-instrument-dialog/add-instrument-dialog.component';
import {MatSelectModule} from "@angular/material/select";
import {CourseComponent} from "./course/course.component";
import {CourseEditDialogComponent} from "./course/course-edit-dialog/course-edit-dialog.component";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";
import {InstrumentEditDialogComponent} from "./instrument/instrument-edit-dialog/instrument-edit-dialog.component";
import {StudentComponent} from "./student/student.component";
import {StudentEditDialogComponent} from "./student/student-edit-dialog/student-edit-dialog.component";
import {LessonComponent} from "./lesson/lesson.component";
import {LessonEditDialogComponent} from "./lesson/lesson-edit-dialog/lesson-edit-dialog.component";
import {AddStudentToCourseDialogComponent} from "./student/student-edit-dialog/add-student-to-course-dialog/add-student-to-course-dialog.component";
import {ErrorDialogComponent} from "./error-dialog/error-dialog.component";
import {LoginComponent} from "./login/login.component";
import {AuthInterceptor} from "./_service/auth.interceptor";
import {TeachersComponent} from "./teachers/teachers.component";
import {StudentGroupsComponent} from "./student-groups/student-groups.component";
import {AuditComponent} from "./audit/audit.component";
import {LoadComponent} from "./load/load.component";
import {UsersComponent} from "./users/users.component";
import {RegisterComponent} from "./register/register.component";
import {TeacherEditDialogComponent} from "./teachers/teacher-edit-dialog/teacher-edit-dialog.component";
import {StudentGroupEditDialogComponent} from "./student-groups/student-group-edit-dialog/student-group-edit-dialog.component";
import {LoadEditDialogComponent} from "./load/load-edit-dialog/load-edit-dialog.component";

@NgModule({
  declarations: [
    AppComponent,
    RoomComponent,
    InstrumentComponent,
    InstrumentEditDialogComponent,
    RoomEditDialogComponent,
    AddInstrumentDialogComponent,
    CourseComponent,
    CourseEditDialogComponent,
    StudentComponent,
    StudentEditDialogComponent,
    LessonComponent,
    LessonEditDialogComponent,
    AddStudentToCourseDialogComponent,
    ErrorDialogComponent,
    LoginComponent,

    RegisterComponent,
    TeachersComponent,
    StudentGroupsComponent,
    LoadComponent,
    UsersComponent,
    AuditComponent,

    TeacherEditDialogComponent,
    StudentGroupEditDialogComponent,
    LoadEditDialogComponent,
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
