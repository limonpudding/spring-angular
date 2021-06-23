import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {SelectionModel} from "@angular/cdk/collections";
import {UserService} from "../../_service/user.service";
import {User} from "../../_model/user";

@Component({
  selector: 'app-user-edit-dialog',
  templateUrl: './user-edit-dialog.component.html',
  styleUrls: ['./user-edit-dialog.component.scss']
})
export class UserEditDialogComponent implements OnInit {

  data: User = new User();
  selection = new SelectionModel(false, []);

  constructor(
    private _userService: UserService,
    public dialogRef: MatDialogRef<UserEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public user: User,
    public dialog: MatDialog) {
    if (this.user) {
      this.data = this.user;
    }
  }

  ngOnInit(): void {
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {
    if (this.data.idd) {
      this._userService.updateUser(this.data.idd, this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    } else {
      this._userService.createUser(this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    }
  }
}
