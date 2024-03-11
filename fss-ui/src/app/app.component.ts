import { Component } from '@angular/core';
import {AuthzService} from "./services/authz.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {

  constructor(protected authzSvc: AuthzService) {
  }
  title = 'Job Hunt Management System';
}
