import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {TeacherService} from "../_service/teacher.service";
import {MatDialog} from "@angular/material/dialog";
import {SelectionModel} from "@angular/cdk/collections";
import {User} from "../_model/user";

@Component({
  selector: 'app-teachers',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements AfterViewInit {

  sizeOption: number[] = [2, 5, 10];
  displayedColumns: string[] = ['select', 'idd', 'login', 'status', 'lastLoginDate'];
  data: Array<User> = [];
  selection = new SelectionModel<User>(false, []);

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private _teacherService: TeacherService, public dialog: MatDialog) {
    this.data = [];
    let item = new User();
    item.idd = 1;
    item.login = 'admin';
    item.status = 'Предоставлены';
    item.lastLoginDate = '02.04.2021 15:21';
    let item2 = new User();
    item2.idd = 1;
    item2.login = 'sergei';
    item2.status = 'Ожидает подтверждения';
    item2.lastLoginDate = '-';
    this.data.push(item);
    this.data.push(item2);

    this.isLoadingResults = false;
  }

  ngAfterViewInit() {

  }

  refresh() {

  }
}
