import {Component, OnDestroy} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CompaniesServiceV1Service, V1Company, V1YN} from "../../generated/api_client";
import {Subscription} from "rxjs";

function makeNewCompany():V1Company {
  return {banned: V1YN.N, createdAt: "", id: "", name: "", notes: "", sourceId: ""};
}

@Component({
  selector: 'app-company',
  templateUrl: './company.component.html'
})
export class CompanyComponent implements OnDestroy{
  private sub: Subscription;
  id: string = '';
  company: V1Company = makeNewCompany()

  constructor(private companiesSvc: CompaniesServiceV1Service, private route: ActivatedRoute,private router: Router) {
    this.sub = this.route.paramMap.subscribe(params => {
      this.id = params.get('id') || 'no-id-param';
      if( 'new' == this.id ){
        this.company = makeNewCompany();
      }else {
        this.companiesSvc.getCompanyById(this.id).subscribe(c => {
          this.company = c;
        });
      }
    })
  }

  ngOnDestroy(): void {
        if( this.sub ){
          this.sub.unsubscribe();
        }
    }

  saveCompany() {
    this.companiesSvc.createCompany(this.company).subscribe(c => {
      this.company = c;
    })
  }

  cancel() {
    this.router.navigate(['/companies']);
  }
}
