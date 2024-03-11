import { Component } from '@angular/core';
import {CompaniesServiceV1Service, V1Company} from "../../generated/api_client";
import {Router} from "@angular/router";

@Component({
  selector: 'app-companies-list',
  templateUrl: './companies-list.component.html',
})
export class CompaniesListComponent {

  companies: Array<V1Company> = [];

  constructor(private companiesSvc: CompaniesServiceV1Service, private router: Router) {
    this.refresh();
  }


  refresh() {
    this.companiesSvc.getAllCompanies().subscribe(svcs => {
      this.companies = svcs;
    });
  }

  newCompany() {
    this.router.navigate(['/companies/new']);
  }
}
