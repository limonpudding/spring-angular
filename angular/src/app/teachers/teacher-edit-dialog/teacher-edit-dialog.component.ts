import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {SelectionModel} from "@angular/cdk/collections";
import {Teacher} from "../../_model/teacher";
import {TeacherService} from "../../_service/teacher.service";

@Component({
  selector: 'app-teacher-edit-dialog',
  templateUrl: './teacher-edit-dialog.component.html',
  styleUrls: ['./teacher-edit-dialog.component.scss']
})
export class TeacherEditDialogComponent implements OnInit {

  data: Teacher = new Teacher();
  selection = new SelectionModel(false, []);

  constructor(
    private _teacherService: TeacherService,
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
    if (this.data.idd) {
      this._teacherService.updateTeacher(this.data.idd, this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    } else {
      this._teacherService.createTeacher(this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    }
  }
}
