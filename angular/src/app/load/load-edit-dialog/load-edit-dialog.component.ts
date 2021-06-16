import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {SelectionModel} from "@angular/cdk/collections";
import {StudentGroupService} from "../../_service/student-group.service";
import {TeacherService} from "../../_service/teacher.service";
import {LoadService} from "../../_service/load.service";
import {MatTable} from "@angular/material/table";
import {Teacher} from "../../_model/teacher";
import {StudentGroup} from '../../_model/student-group'
import {Load} from "../../_model/load";

@Component({
  selector: 'app-load-edit-dialog',
  templateUrl: './load-edit-dialog.component.html',
  styleUrls: ['./load-edit-dialog.component.scss']
})
export class LoadEditDialogComponent implements OnInit {
  @ViewChild(MatTable) courseTable: MatTable<Load>;

  data: Load = new Load();
  selection = new SelectionModel(false, []);
  studentGroups: StudentGroup[];
  selectedStudentGroup: StudentGroup;
  teachers: Teacher[];
  selectedTeacher: Teacher;

  constructor(
    private _loadService: LoadService,
    private _studentService: StudentGroupService,
    private _teacherService: TeacherService,
    public dialogRef: MatDialogRef<LoadEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public load: Load,
    public dialog: MatDialog) {
    if (this.load) {
      this.data = this.load;
    }

  }

  ngOnInit(): void {
    this._studentService.getStudentGroupList(null, null, 0, 1000)
      .pipe()
      .subscribe(res => this.studentGroups = res.list);
    this._teacherService.getTeacherList(null, null, 0, 1000)
      .pipe()
      .subscribe(res => this.teachers = res.list);
    this.selectedTeacher = this.teachers.find((element) =>  element.idd == this.data.teacheridd)
    this.selectedStudentGroup = this.studentGroups.find((element) =>  element.idd == this.data.studentGroupidd)
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {
    this.data.studentGroupidd = this.selectedStudentGroup.idd
    this.data.teacheridd = this.selectedTeacher.idd
    if (this.data.idd) {
      this._loadService.updateLoad(this.data.idd, this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    } else {
      this._loadService.createLoad(this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    }
  }
}
