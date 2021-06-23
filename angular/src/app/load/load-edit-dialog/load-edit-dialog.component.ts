import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {SelectionModel} from "@angular/cdk/collections";
import {StudentGroupService} from "../../_service/student-group.service";
import {TeacherService} from "../../_service/teacher.service";
import {LoadService} from "../../_service/load.service";
import {MatTable} from "@angular/material/table";
import {Teacher} from "../../_model/teacher";
import {StudentGroup} from '../../_model/student-group'
import {loadEditSelectableValue} from '../../_model/load-edit-selectable-values'
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
  studentGroups: loadEditSelectableValue[];
  teachers: loadEditSelectableValue[];
  selectedTeacher: loadEditSelectableValue;
  selectedStudentGroup: loadEditSelectableValue;

  constructor(
    private _loadService: LoadService,
    private _studentService: StudentGroupService,
    private _teacherService: TeacherService,
    public dialogRef: MatDialogRef<LoadEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public load: { load: Load, teachers: Teacher[], studentGroups: StudentGroup[] },
    public dialog: MatDialog) {
    if (this.load) {
      var adaptedTeachers: loadEditSelectableValue[] = new Array();
      var adaptedStudentGroups: loadEditSelectableValue[] = new Array();
      this.load.teachers.forEach(
        (value) => {
          let newValueTeacher = new loadEditSelectableValue();
          newValueTeacher.value = value.lastName + " " + value.firstName + " " + value.middleName
          newValueTeacher.idd = value.idd
          adaptedTeachers.push(newValueTeacher)
        }
      )
      this.load.studentGroups.forEach(
        (value) => {
          let newValueGroup = new loadEditSelectableValue();
          newValueGroup.value = value.branch + " " + value.specialty
          newValueGroup.idd = value.idd
          adaptedStudentGroups.push(newValueGroup)
        }
      )

      this.data = this.load.load;
      this.teachers = adaptedTeachers;
      this.studentGroups = adaptedStudentGroups;
    }
  }

  ngOnInit(): void {
    this.selectedTeacher = this.teachers.find((element) => element.idd == this.data.teacherIdd)
    this.selectedStudentGroup = this.studentGroups.find((element) => element.idd == this.data.studentGroupIdd)
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {
    this.data.studentGroupIdd = this.selectedStudentGroup.idd
    this.data.teacherIdd = this.selectedTeacher.idd
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
