import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {TeacherService} from "../_service/teacher.service";
import {MatDialog} from "@angular/material/dialog";
import {SelectionModel} from "@angular/cdk/collections";
import {merge, of as observableOf} from "rxjs";
import {catchError, map, startWith, switchMap} from "rxjs/operators";
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
  }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    this.refresh();
  }

  refresh() {
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this._teacherService.getTeacherList(
            this.sort.active, this.sort.direction, this.paginator.pageIndex, this.paginator.pageSize);
        }),
        map(data => {
          this.isLoadingResults = false;
          this.isRateLimitReached = false;
          this.resultsLength = data.totalCount;

          return data.list;
        }),
        catchError(() => {
          this.isLoadingResults = false;
          this.isRateLimitReached = true;
          return observableOf([]);
        })
      ).subscribe(data => this.data = data);
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
    if (this.selection.selected[0] == null) {
      return;
    }
    this._teacherService.deleteTeacherByIdd(this.selection.selected[0].idd).toPromise()
      .then(res => {
        this.selection.clear();
        this.refresh()
      })
      .catch(error => console.log(error));
  }
}
