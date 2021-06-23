import {Component} from '@angular/core';
import {AuthService} from "./_service/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'angular';
  authentificated: boolean;

  constructor(private _authService: AuthService) {
    _authService.getCurrentUser()
      .pipe()
      .subscribe(res => {
        if (res) {
          this.authentificated = true;
        } else {
          this.authentificated = false;
        }
      })
  }
}
