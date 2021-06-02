import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {SelectionModel} from "@angular/cdk/collections";
import {StudentService} from "../../_service/student.service";
import {NativeDateAdapter} from '@angular/material/core';
import {formatDate} from '@angular/common';
import {CourseList} from "../../_model/course-list";
import {MatTable} from "@angular/material/table";
import {Teacher} from "../../_model/teacher";
import {Load} from "../../_model/load";

@Component({
  selector: 'app-load-edit-dialog',
  templateUrl: './load-edit-dialog.component.html',
  styleUrls: ['./load-edit-dialog.component.scss']
})
export class LoadEditDialogComponent implements OnInit {
  @ViewChild(MatTable) courseTable: MatTable<CourseList>;

  data: Load = new Load();
  selection = new SelectionModel(false, []);

  constructor(
    private _studentService: StudentService,
    public dialogRef: MatDialogRef<LoadEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public load: Load,
    public dialog: MatDialog) {
    if (this.load) {
      this.data = this.load;
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
