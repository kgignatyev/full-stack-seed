import {Component, ViewChild} from '@angular/core';
import DevExpress from "devextreme";
import CustomStore from "devextreme/data/custom_store";
import DataSource from "devextreme/data/data_source";
import {
  JobsServiceV1Service,
  V1CompanyResponse,
  V1Job,
  V1JobListResult,
  V1SearchRequest
} from "../../generated/api_client";
import {AuthzService} from "../../services/authz.service";
import {DxDataGridComponent} from "devextreme-angular";
import {ContextService} from "../../services/context.service";

@Component({
  selector: 'app-jobs-list',
  templateUrl: './jobs-list.component.html'
})
export class JobsListComponent {

  private jobsDataStore: DevExpress.data.CustomStore<any, any>;
  jobsDataSource: DevExpress.data.DataSource<V1Job, string>;
  pageSize = 20;
  companyResponsePopupVisible = false;
  searchRequest: V1SearchRequest = {pagination: {
      offset: 0,
      limit: this.pageSize
    }, searchExpression: "",sortExpression: ""};

  @ViewChild('jobsDataGrid', {static: false}) jobsDataGrid: DxDataGridComponent | undefined;


  constructor( private jobsService: JobsServiceV1Service, private authz: AuthzService, private cxtSvc:ContextService) {
    this.jobsDataStore = new CustomStore({
      key: 'id',
      useDefaultSearch: true,
      load: (loadOptions) => {
        /*
           'filter',
           'group',
           'groupSummary',
           'parentIds',
           'requireGroupCount',
           'requireTotalCount',
           'searchExpr',
           'searchOperation',
           'searchValue',
           'select',
           'sort',
           'skip',
           'take',
           'totalSummary',
           'userData' */

        console.info("loadOptions",loadOptions)
        if (loadOptions['skip']) {
          this.searchRequest.pagination.offset = loadOptions['skip']
        } else {
          this.searchRequest.pagination.offset = 0
        }
        if (loadOptions['take']) {
          this.searchRequest.pagination.limit = loadOptions['take']
        } else {
          this.searchRequest.pagination.limit = this.pageSize
        }

        if (loadOptions['filter']) {
          const filters:string[] = []
          loadOptions['filter'].forEach((ff: any) => {
            if (Array.isArray(ff)) {
              const fMatcher = ff[0]  + ' ilike "%' + ff[2] + '%"'
              filters.push(fMatcher)
            }
          })
          this.searchRequest.searchExpression = filters.join(" OR ")
        }else{
          this.searchRequest.searchExpression = ""
        }
        if( loadOptions['sort'] ){
          const sorts = loadOptions['sort'] as Array<any>
          this.searchRequest.sortExpression = sorts.map((s) => s.selector + " " + (s.desc ? "desc" : "asc")).join(",")
        }else {
          this.searchRequest.sortExpression = "createdAt desc"
        }

        console.info("search request",this.searchRequest)
        if( !this.authz.idToken$.getValue() ){
          console.info("No token")
          return Promise.resolve({
            data: [],
            totalCount: 0,
            summary: [],
            groupCount: 0
          })
        }
        return this.jobsService.searchJobs(this.searchRequest)
          .toPromise()
          .then(response => {
            // @ts-ignore
            const data: V1JobListResult = response
            console.info( "Jobs found: " + data.items.length)
            return {
              data: data.items,
              totalCount: data.listSummary.total,
              summary: [],
              groupCount: 0
            };
          })
          .catch(() => {
            throw 'Data loading error'
          });
      },

    });
    this.jobsDataSource = new DataSource({
      store: this.jobsDataStore,
    });
  }

  refresh(){
    this.jobsDataGrid?.instance.refresh().then(d => console.info("Refreshed"))
  }

  newJob() {
    this.cxtSvc.infoAlert("Let's create a new job")
  }

  apply(j: V1Job) {
    const jClone = Object.assign({}, j)
    jClone.status = "APPLIED"
    this.updateJob(j,jClone)
  }
  reject(j: V1Job) {
    const jClone = Object.assign({}, j)
    jClone.status = "NOT_INTERESTED"
    this.updateJob(j,jClone)
  }

  updateJob(j: V1Job, jClone: V1Job) {
    this.jobsService.updateJobById( j.id, jClone).subscribe(r => {
      Object.assign(j, r)
    })
  }

  selectedJob:V1Job | undefined

  companyResponse(j:V1Job) {
    this.selectedJob = j
    this.companyResponsePopupVisible = true
  }

  companyResponses:V1CompanyResponse[] = [ 'NONE', 'REJECTED', 'FILLED', 'CANCELLED']

  recordCompanyResponse( r:V1CompanyResponse ) {

    if( this.selectedJob ){
      const jClone = Object.assign({}, this.selectedJob)
      jClone.companyResponse = r
      this.jobsService.updateJobById( this.selectedJob.id, jClone).subscribe(j => {
        // @ts-ignore
        this.selectedJob.companyResponse = j.companyResponse
        this.companyResponsePopupVisible = false
      })
    }

  }

}
