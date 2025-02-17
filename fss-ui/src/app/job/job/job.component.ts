import {Component, OnDestroy, TemplateRef, ViewChild} from '@angular/core';
import {ContextService} from "../../services/context.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthzService} from "../../services/authz.service";
import {Subscription} from "rxjs";
import {JobsServiceV1Service, V1Job} from "../../generated/api_client";
import {SimpleItem, SimpleItemTemplateData} from "devextreme/ui/form";
import DevExpress from "devextreme";
import {DxElement} from "devextreme/core/element";

@Component({
    selector: 'app-job',
    templateUrl: './job.component.html',
    standalone: false
})
export class JobComponent implements OnDestroy {
  private sub: Subscription;
  id!: string;
  job!: V1Job;
  jobEvents: any[] = [];

  @ViewChild('jobForm', {static: false}) jobForm!: DevExpress.ui.dxForm ;

  customizeJobFormItem= (item: SimpleItem) => {
    if (item.dataField == 'title') {
      item.validationRules = [
        {type: 'required', message: 'Title is required'},
        {type: 'stringLength', max: 100, message: 'Title should be less than 100 characters'},
        {type: 'stringLength', min: 5, message: 'Title should be at least 5 characters'}
      ]
    }

    if( item.dataField == 'id' || item.dataField == 'accountId') {
      item.editorOptions = {readOnly: true  }
    }

    if(item.dataField == 'events') {
      item.visible = false;
    }

    if(item.dataField == 'sourceId') {

      item.template = 'jobSourceTemplate'
    }

  }

  constructor(private authzSvc: AuthzService,
              private jobSvc: JobsServiceV1Service,
              private route: ActivatedRoute,
              private router: Router,
              private cxtSvc: ContextService) {
    this.sub = this.route.paramMap.subscribe(params => {
      this.id = params.get('id') || 'no-id-param';
      if (this.id == 'new') {
        this.jobEvents = [];
        this.job = {
          accountId: this.cxtSvc.currentAccount$.getValue(), notes: "", sourceId: "", id: '', title: '',
          status: "APPLIED", createdAt: new Date().toISOString()
        };
      } else {
        this.authzSvc.userAuth0$.subscribe(user => {
          this.refreshJob();
        })
      }

    });
  }

  private refreshJob() {
    this.jobEvents = []
    this.jobSvc.getJobById(this.id).subscribe(j => {
      this.job = j;
      this.jobEvents = j.events || [];
    })
  }

  ngOnDestroy(): void {
    if (this.sub) {
      this.sub.unsubscribe();
    }
  }

  cancel() {
    this.router.navigate(['/jobs']);
  }

  saveJob() {
    // @ts-ignore
    const validationResults = this.jobForm.instance.validate()
    if( validationResults.isValid) {
      this.jobSvc.updateJobById(this.id, this.job).subscribe(j => {
        this.job = j;
      })
    }
  }
}
