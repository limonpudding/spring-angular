import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {SelectionModel} from "@angular/cdk/collections";
import {MatTable} from "@angular/material/table";
import {Load} from "../../_model/load";
import {StudentGroupService} from "../../_service/student-group.service";

@Component({
  selector: 'app-load-edit-dialog',
  templateUrl: './load-edit-dialog.component.html',
  styleUrls: ['./load-edit-dialog.component.scss']
})
export class LoadEditDialogComponent implements OnInit {
  @ViewChild(MatTable) courseTable: MatTable<Load>;

  data: Load = new Load();
  selection = new SelectionModel(false, []);

  constructor(
    private _studentGroupService: StudentGroupService,
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
