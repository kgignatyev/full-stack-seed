import {Component, OnDestroy, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CompaniesServiceV1Service, V1Company, V1YN} from "../../generated/api_client";
import {Subscription} from "rxjs";
import {ContextService} from "../../services/context.service";
import dxForm, {SimpleItem} from "devextreme/ui/form";

function makeNewCompany():V1Company {
  return {banned: V1YN.N, createdAt: new Date().toISOString(),
    accountId: "my",
    id: "", name: "", notes: "", source: ""};
}

@Component({
    selector: 'app-company',
    templateUrl: './company.component.html',
    standalone: false
})
export class CompanyComponent implements OnDestroy{
  private sub: Subscription;
  id: string = '';
  company: V1Company = makeNewCompany()

  @ViewChild('companyForm', {static: false})
  companyForm!: dxForm ;

  constructor(private companiesSvc: CompaniesServiceV1Service, private cxtSvc: ContextService,
              private route: ActivatedRoute,private router: Router) {
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

  customizeCompanyFormItem(item: SimpleItem) {
    if( item.dataField == 'id' || item.dataField == 'accountId') {
      item.editorOptions = {readOnly: true  }
    }

    if( item.dataField == 'name' ) {


      item.validationRules = [
        {type: 'required', message: 'Name is required'},
        {type: 'stringLength', max: 100, message: 'Name should be less than 100 characters'},
        {type: 'stringLength', min: 5, message: 'Name should be at least 5 characters'}
      ]
    }

  }

  ngOnDestroy(): void {
        if( this.sub ){
          this.sub.unsubscribe();
        }
    }

  saveCompany() {

    // @ts-ignore
    const validationResult = this.companyForm.instance.validate();
    if( validationResult.isValid ) {
      this.companiesSvc.createCompany(this.cxtSvc.currentAccount$.getValue(), this.company).subscribe(c => {
        this.company = c;
        this.router.navigate(['/companies']);
      })
    }
  }

  cancel() {
    this.router.navigate(['/companies']);
  }
}
