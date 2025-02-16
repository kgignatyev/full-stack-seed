import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CompaniesListComponent} from "./company/companies-list/companies-list.component";
import {CompanyComponent} from "./company/company/company.component";
import {JobsListComponent} from "./job/jobs-list/jobs-list.component";
import {JobComponent} from "./job/job/job.component";
import {AboutComponent} from "./system/about/about.component";

const routes: Routes = [

  {path: 'companies', component: CompaniesListComponent},
  {path: 'companies/:id', component: CompanyComponent},
  {path: 'jobs', component: JobsListComponent},
  {path: 'jobs/:id', component: JobComponent},
  {path: '', component: AboutComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
