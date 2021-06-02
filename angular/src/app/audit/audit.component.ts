import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {StudentService} from "../_service/student.service";
import {MatDialog} from "@angular/material/dialog";
import {SelectionModel} from "@angular/cdk/collections";
import {StudentGroup} from "../_model/student-group";
import {Audit} from "../_model/audit";

@Component({
  selector: 'app-audit',
  templateUrl: './audit.component.html',
  styleUrls: ['./audit.component.scss']
})
export class AuditComponent implements AfterViewInit {

  sizeOption: number[] = [2, 5, 10];
  displayedColumns: string[] = ['select', 'idd', 'login', 'actionDescription', 'sqlText', 'status', 'dateAndTime'];
  data: Audit[];
  selection = new SelectionModel<Audit>(false, []);

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private _studentService: StudentService, public dialog: MatDialog) {
    this.data = [];
    let item = new Audit();
    item.idd = 1;
    item.login = 'admin';
    item.actionDescription = '<Описание действия>';
    item.sqlText = '<SQL запрос>';
    item.status = '<Статус>';
    item.dateAndTime = '02.04.2021 15:21';
    this.data.push(item);

    this.isLoadingResults = false;
  }

  ngAfterViewInit() {
  }


  refresh() {

  }
}
