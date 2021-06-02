import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {TeacherService} from "../_service/teacher.service";
import {MatDialog} from "@angular/material/dialog";
import {SelectionModel} from "@angular/cdk/collections";
import {Teacher} from "../_model/teacher";
import {TeacherEditDialogComponent} from "./teacher-edit-dialog/teacher-edit-dialog.component";

@Component({
  selector: 'app-teachers',
  templateUrl: './teachers.component.html',
  styleUrls: ['./teachers.component.scss']
})
export class TeachersComponent implements AfterViewInit {

  sizeOption: number[] = [2, 5, 10];
  displayedColumns: string[] = ['select', 'idd', 'lastName', 'firstName', 'middleName', 'phoneNumber', 'experience'];
  data: Array<Teacher> = [];
  selection = new SelectionModel<Teacher>(false, []);

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private _teacherService: TeacherService, public dialog: MatDialog) {
    this.data = [];
    let item = new Teacher();
    item.idd = 1;
    item.lastName = 'Старкова';
    item.firstName = 'Ольга';
    item.middleName = 'Леонидовна';
    item.phoneNumber = '+79191002030';
    item.experience = '7 лет';
    this.data.push(item);

    this.isLoadingResults = false;
  }

  ngAfterViewInit() {

  }

  refresh() {

  }

  openCreateDialog() {
    const dialogRef = this.dialog.open(TeacherEditDialogComponent, {
      width: '750px'
    });

    dialogRef.afterClosed().subscribe(result => {
      this.refresh();
    });
  }

  openEditDialog() {
    if (this.selection.selected[0] == null) {
      return;
    }
    const dialogRef = this.dialog.open(TeacherEditDialogComponent, {
      width: '750px',
      data: this.selection.selected[0]
    });
    this.selection.clear();
    dialogRef.afterClosed().subscribe(result => {
      this.refresh();
    });
  }

  deleteTeacher() {
    this.refresh();
  }
}
