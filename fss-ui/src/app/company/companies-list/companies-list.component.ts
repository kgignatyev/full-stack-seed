import { Component } from '@angular/core';
import {CompaniesServiceV1Service, V1Company} from "../../generated/api_client";
import {Router} from "@angular/router";
import {ContextService} from "../../services/context.service";

@Component({
    selector: 'app-companies-list',
    templateUrl: './companies-list.component.html',
    standalone: false
})
export class CompaniesListComponent {

  companies: Array<V1Company> = [];
  pageSize: number = 15;

  constructor(private companiesSvc: CompaniesServiceV1Service,
              private cxtSvc:ContextService,
              private router: Router) {
    this.refresh();
  }


  refresh() {
    this.companiesSvc.getAllCompanies(this.cxtSvc.currentAccount$.getValue() ).subscribe(svcs => {
      this.companies = svcs;
    });
  }

  newCompany() {
    this.router.navigate(['/companies/new']);
  }
}
