import { Component } from '@angular/core';
import {AuthzService} from "../../services/authz.service";

@Component({
  selector: 'app-about',
  standalone: false,
  templateUrl: './about.component.html'
})
export class AboutComponent {

  constructor( protected authzService: AuthzService ) {
  }

}
