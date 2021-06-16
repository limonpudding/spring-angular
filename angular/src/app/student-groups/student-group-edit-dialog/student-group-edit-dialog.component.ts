import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {SelectionModel} from "@angular/cdk/collections";
import {MatTable} from "@angular/material/table";
import {StudentGroup} from "../../_model/student-group";
import {StudentGroupService} from "../../_service/student-group.service";

@Component({
  selector: 'app-student-group-edit-dialog',
  templateUrl: './student-group-edit-dialog.component.html',
  styleUrls: ['./student-group-edit-dialog.component.scss']
})
export class StudentGroupEditDialogComponent implements OnInit {
  @ViewChild(MatTable) courseTable: MatTable<StudentGroup>;

  data: StudentGroup = new StudentGroup();
  selection = new SelectionModel(false, []);

  constructor(
    private _studentGroupService: StudentGroupService,
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
    if (this.data.idd) {
      this._studentGroupService.updateStudentGroup(this.data.idd, this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    } else {
      this._studentGroupService.createStudentGroup(this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    }
  }
}
