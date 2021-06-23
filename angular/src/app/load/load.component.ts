import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {StudentGroupService} from "../_service/student-group.service";
import {MatDialog} from "@angular/material/dialog";
import {SelectionModel} from "@angular/cdk/collections";
import {LoadService} from "../_service/load.service";
import {Load} from "../_model/load";
import {merge, of as observableOf} from "rxjs";
import {catchError, map, startWith, switchMap} from "rxjs/operators";
import {LoadEditDialogComponent} from "./load-edit-dialog/load-edit-dialog.component";
import {TeacherService} from "../_service/teacher.service";
import {Teacher} from "../_model/teacher";
import {StudentGroup} from '../_model/student-group'

@Component({
  selector: 'app-student-groups',
  templateUrl: './load.component.html',
  styleUrls: ['./load.component.scss']
})
export class LoadComponent implements AfterViewInit {

  sizeOption: number[] = [2, 5, 10];
  displayedColumns: string[] = ['select', 'idd', 'teacher', 'studentGroup', 'hoursCount', 'discipline', 'type', 'wage'];
  data: Load[];
  studentGroups: StudentGroup[];
  teachers: Teacher[];
  selection = new SelectionModel<Load>(false, []);

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private _loadService: LoadService, private _studentService: StudentGroupService,
              private _teacherService: TeacherService, public dialog: MatDialog) {
  }

  ngAfterViewInit() {
    this._teacherService.getTeacherList(null, null, 0, 1000)
      .pipe()
      .subscribe(res => this.teachers = res.list);
    this._studentService.getStudentGroupList(null, null, 0, 1000)
      .pipe()
      .subscribe(res => this.studentGroups = res.list);

    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    this.refresh();
  }


  refresh() {
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this._loadService.getLoadList(
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
      ).subscribe(data => {
        for (let item of data) {
          let selectedTeacher = this.teachers.find((element) => element.idd == item.teacherIdd)
          let selectedStudentGroup = this.studentGroups.find((element) => element.idd == item.studentGroupIdd)
          item.teacher_name = selectedTeacher.lastName + " " + selectedTeacher.firstName + " " + selectedTeacher.middleName
          item.studentGroup_name = selectedStudentGroup.branch + " " + selectedStudentGroup.specialty
        }
        this.data = data
      }
    );
  }

  openCreateDialog() {
    const dialogRef = this.dialog.open(LoadEditDialogComponent, {
      width: '750px',
      data: {
        load: new Load(),
        teachers: this.teachers,
        studentGroups: this.studentGroups
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.refresh();
    });
  }

  openEditDialog() {
    if (this.selection.selected[0] == null) {
      return;
    }
    const dialogRef = this.dialog.open(LoadEditDialogComponent, {
      width: '750px',
      data: {
        load: this.selection.selected[0],
        teachers: this.teachers,
        studentGroups: this.studentGroups
      }

    });
    this.selection.clear();
    dialogRef.afterClosed().subscribe(result => {
      this.refresh();
    });
  }

  deleteLoad() {
    if (this.selection.selected[0] == null) {
      return;
    }
    this._loadService.deleteLoadByIdd(this.selection.selected[0].idd).toPromise()
      .then(res => {
        this.selection.clear();
        this.refresh()
      })
      .catch(error => console.log(error));
  }
}
