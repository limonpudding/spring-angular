import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatDialog} from "@angular/material/dialog";
import {SelectionModel} from "@angular/cdk/collections";
import {StudentGroup} from "../_model/student-group";
import {Load} from "../_model/load";
import {LoadEditDialogComponent} from "./load-edit-dialog/load-edit-dialog.component";
import {StudentGroupService} from "../_service/student-group.service";

@Component({
  selector: 'app-student-groups',
  templateUrl: './load.component.html',
  styleUrls: ['./load.component.scss']
})
export class LoadComponent implements AfterViewInit {

  sizeOption: number[] = [2, 5, 10];
  displayedColumns: string[] = ['select', 'idd', 'teacher', 'studentGroup', 'hoursCount', 'discipline', 'type', 'wage'];
  data: Load[];
  selection = new SelectionModel<Load>(false, []);

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private _studentGroupService: StudentGroupService, public dialog: MatDialog) {
    this.data = [];
    let item = new Load();
    item.idd = 1;
    item.teacher = 'Старкова Ольга Леонидовна';
    item.studentGroup = 'Мех-Мат';
    item.hoursCount = 120;
    item.discipline = 'Компьюетрная безопасность';
    item.type = 'Лекция';
    item.wage = 500;
    let item2 = new Load();
    item2.idd = 1;
    item2.teacher = 'Старкова Ольга Леонидовна';
    item2.studentGroup = 'Мех-Мат';
    item2.hoursCount = 60;
    item2.discipline = 'Компьюетрная безопасность';
    item2.type = 'Практика';
    item2.wage = 500;
    this.data.push(item);
    this.data.push(item2);

    this.isLoadingResults = false;
  }

  ngAfterViewInit() {
  }


  refresh() {

  }

  openCreateDialog() {
    const dialogRef = this.dialog.open(LoadEditDialogComponent, {
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
    const dialogRef = this.dialog.open(LoadEditDialogComponent, {
      width: '750px',
      data: this.selection.selected[0]
    });
    this.selection.clear();
    dialogRef.afterClosed().subscribe(result => {
      this.refresh();
    });
  }

  deleteLoad() {
    this.refresh();
  }
}
