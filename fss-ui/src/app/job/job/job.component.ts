import { Component, OnDestroy } from '@angular/core';
import {ContextService} from "../../services/context.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthzService} from "../../services/authz.service";
import {Subscription} from "rxjs";
import {JobsServiceV1Service, V1Job} from "../../generated/api_client";

@Component({
  selector: 'app-job',
  templateUrl: './job.component.html'
})
export class JobComponent implements OnDestroy {
  private sub: Subscription;
  id!: string;
  job!: V1Job;
  constructor(private authzSvc: AuthzService,
              private jobSvc: JobsServiceV1Service,
              private route: ActivatedRoute,
              private router: Router,
              private cxtSvc: ContextService) {
    this.sub = this.route.paramMap.subscribe(params => {
      this.id = params.get('id') || 'no-id-param';
      this.authzSvc.userAuth0$.subscribe(user => {
        this.refreshJob();
      })

    });
  }

  private refreshJob() {
    this.jobSvc.getJobById(this.id).subscribe(j => {
      this.job = j;
    })
  }

  ngOnDestroy(): void {
    if (this.sub) {
      this.sub.unsubscribe();
    }
  }

  cancel() {
    this.refreshJob()
  }

  saveJob() {
    this.jobSvc.updateJobById(this.id, this.job).subscribe(j => {
      this.job = j;
    })
  }
}
