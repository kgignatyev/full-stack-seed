import {Component, OnInit} from '@angular/core';
import {SecurityServiceV1Service, V1SecurityPolicy} from "../../generated/api_client";
import {AuthzService} from "../../services/authz.service";

@Component({
  selector: 'app-system-info',
  standalone: false,
  templateUrl: './system-info.html'
})
export class SystemInfo implements OnInit {
  protected policies: V1SecurityPolicy[] = [];

  constructor(private securitySvc: SecurityServiceV1Service, private authz: AuthzService) {

  }

  ngOnInit(): void {
    this.refresh()
  }

  protected refresh() {

    this.securitySvc.getSecurityPoliciesForUser( "me").subscribe(res => {
      this.policies  = res;
    })
  }
}
