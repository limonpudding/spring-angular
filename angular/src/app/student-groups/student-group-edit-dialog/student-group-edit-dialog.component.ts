import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {SelectionModel} from "@angular/cdk/collections";
import {StudentService} from "../../_service/student.service";
import {NativeDateAdapter} from '@angular/material/core';
import {formatDate} from '@angular/common';
import {CourseList} from "../../_model/course-list";
import {MatTable} from "@angular/material/table";
import {Teacher} from "../../_model/teacher";
import {StudentGroup} from "../../_model/student-group";

@Component({
  selector: 'app-student-group-edit-dialog',
  templateUrl: './student-group-edit-dialog.component.html',
  styleUrls: ['./student-group-edit-dialog.component.scss']
})
export class StudentGroupEditDialogComponent implements OnInit {
  @ViewChild(MatTable) courseTable: MatTable<CourseList>;

  data: StudentGroup = new StudentGroup();
  selection = new SelectionModel(false, []);

  constructor(
    private _studentService: StudentService,
    public dialogRef: MatDialogRef<StudentGroupEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public studentGroup: StudentGroup,
    public dialog: MatDialog) {
    if (this.studentGroup) {
      this.data = this.studentGroup;
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
