import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {SelectionModel} from "@angular/cdk/collections";
import {StudentService} from "../../_service/student.service";
import {NativeDateAdapter} from '@angular/material/core';
import {formatDate} from '@angular/common';
import {CourseList} from "../../_model/course-list";
import {MatTable} from "@angular/material/table";
import {Teacher} from "../../_model/teacher";

@Component({
  selector: 'app-teacher-edit-dialog',
  templateUrl: './teacher-edit-dialog.component.html',
  styleUrls: ['./teacher-edit-dialog.component.scss']
})
export class TeacherEditDialogComponent implements OnInit {
  @ViewChild(MatTable) courseTable: MatTable<CourseList>;

  data: Teacher = new Teacher();
  selection = new SelectionModel(false, []);

  constructor(
    private _studentService: StudentService,
    public dialogRef: MatDialogRef<TeacherEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public teacher: Teacher,
    public dialog: MatDialog) {
    if (this.teacher) {
      this.data = this.teacher;
    }

  }

  ngOnInit(): void {
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {

  }
}
