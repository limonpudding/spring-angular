import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatDialog} from "@angular/material/dialog";
import {SelectionModel} from "@angular/cdk/collections";
import {StudentGroup} from "../_model/student-group";
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
    this.data = [];
    let item = new StudentGroup();
    item.idd = 1;
    item.specialty = 'Компьютерная безопасность';
    item.branch = 'Мех-Мат';
    item.count = 9;
    this.data.push(item);

    this.isLoadingResults = false;
  }

  ngAfterViewInit() {
  }


  refresh() {

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
    this.refresh();
  }
}
