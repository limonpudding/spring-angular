import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatDialog} from "@angular/material/dialog";
import {SelectionModel} from "@angular/cdk/collections";
import {User} from "../_model/user";
import {merge, of as observableOf} from "rxjs";
import {catchError, map, startWith, switchMap} from "rxjs/operators";
import {UserService} from "../_service/user.service";
import {UserEditDialogComponent} from "./user-edit-dialog/user-edit-dialog.component";

@Component({
  selector: 'app-teachers',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements AfterViewInit {

  // users: UserList[];
  // selectedUser: UserList;

  sizeOption: number[] = [2, 5, 10];
  displayedColumns: string[] = ['select', 'idd', 'login', 'fio', 'is_active', 'lastLoginDate'];
  data: Array<User> = [];
  selection = new SelectionModel<User>(false, []);

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private _userService: UserService, public dialog: MatDialog) {
  }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    this.refresh();
  }

  openCreateDialog() {
    const dialogRef = this.dialog.open(UserEditDialogComponent, {
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
    const dialogRef = this.dialog.open(UserEditDialogComponent, {
      width: '750px',
      data: this.selection.selected[0]
    });
    this.selection.clear();
    dialogRef.afterClosed().subscribe(result => {
      this.refresh();
    });
  }

  deleteUser() {
    if (this.selection.selected[0] == null) {
      return;
    }
    this._userService.deleteUserByIdd(this.selection.selected[0].idd).toPromise()
      .then(res => {
        this.selection.clear();
        this.refresh()
      })
      .catch(error => console.log(error));
  }

  refresh() {
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this._userService.getUserList(
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

  activateUser() {
    if (this.selection.selected[0] == null) {
      return;
    }
    let selectedUser = this.selection.selected[0];
    selectedUser.isActive = true;
    this._userService.updateUser(selectedUser.idd, selectedUser)
      .toPromise()
      .then(res => this.refresh())
      .catch(error => console.log(error));
  }

  deactivateUser() {
    if (this.selection.selected[0] == null) {
      return;
    }
    let selectedUser = this.selection.selected[0];
    selectedUser.isActive = false;
    this._userService.updateUser(selectedUser.idd, selectedUser)
      .toPromise()
      .then(res => {
        this.selection.clear();
        this.refresh()
      })
      .catch(error => console.log(error));
  }
}
