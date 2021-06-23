import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {first} from "rxjs/operators";
import {AuthService} from "../_service/auth.service";
import {ErrorDialogComponent} from "../error-dialog/error-dialog.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-login',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  loginForm: FormGroup;
  loading = false;
  submitted = false;
  password = new FormControl();
  passPattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*$";

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private _authService: AuthService,
    private dialog: MatDialog
  ) {
    // _authService.getCurrentUser()
    //   .pipe()
    //   .subscribe(res => {
    //     if (res) {
    //       router.navigateByUrl('/teachers');
    //     }
    //   })
  }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      fio: ['', Validators.required],
      login: ['', Validators.required],
      password: ['', Validators.required]
    })
  }

  get f() {
    return this.loginForm.controls;
  }

  onSubmit() {
    this.submitted = true;

    if (this.loginForm.invalid) {
      this.dialog.open(ErrorDialogComponent, {
        width: '750px',
        data: {
          title: 'Некорректные данные!',
          message: 'Пароль должен включать как минимум по 1 заглавной и прописной букве, как минимум 1 цифру и иметь длину не менее 8 символов.'
        }
      });
      return;
    }

    this.loading = true;
    this._authService.register(this.f.fio.value, this.f.login.value, this.f.password.value)
      .pipe(first())
      .subscribe(() => window.location.href = '/teachers', () => this.loading = false);
  }
}
