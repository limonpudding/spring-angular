import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatDialog} from "@angular/material/dialog";
import {SelectionModel} from "@angular/cdk/collections";
import {StudentGroup} from "../_model/student-group";
import {merge, of as observableOf} from "rxjs";
import {catchError, map, startWith, switchMap} from "rxjs/operators";
import {StudentGroupEditDialogComponent} from "./student-group-edit-dialog/student-group-edit-dialog.component";
import {StudentGroupService} from "../_service/student-group.service";

@Component({
  selector: 'app-student-groups',
  templateUrl: './student-groups.component.html',
  styleUrls: ['./student-groups.component.scss']
})
export class StudentGroupsComponent implements AfterViewInit {

  sizeOption: number[] = [2, 5, 10];
  displayedColumns: string[] = ['select', 'idd', 'specialty', 'branch', 'count'];
  data: StudentGroup[];
  selection = new SelectionModel<StudentGroup>(false, []);

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private _studentGroupService: StudentGroupService, public dialog: MatDialog) {
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
          return this._studentGroupService.getStudentGroupList(
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
    const dialogRef = this.dialog.open(StudentGroupEditDialogComponent, {
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
    const dialogRef = this.dialog.open(StudentGroupEditDialogComponent, {
      width: '750px',
      data: this.selection.selected[0]
    });
    this.selection.clear();
    dialogRef.afterClosed().subscribe(result => {
      this.refresh();
    });
  }

  deleteStudentGroup() {
    if (this.selection.selected[0] == null) {
      return;
    }
    this._studentGroupService.deleteStudentGroupByIdd(this.selection.selected[0].idd).toPromise()
      .then(res => {
        this.selection.clear();
        this.refresh()
      })
      .catch(error => console.log(error));
  }
}
