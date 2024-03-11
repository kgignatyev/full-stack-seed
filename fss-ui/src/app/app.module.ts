import {APP_INITIALIZER, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {fssSvcApiModule, fssSvcConfiguration} from "./generated/api_client";
import {
  DxAutocompleteModule,
  DxBoxModule,
  DxButtonModule,
  DxCalendarModule,
  DxChartModule,
  DxCheckBoxModule, DxDataGridModule,
  DxDateBoxModule,
  DxDropDownBoxModule,
  DxDropDownButtonModule,
  DxFormModule,
  DxListModule,
  DxLoadIndicatorModule,
  DxLoadPanelModule,
  DxLookupModule,
  DxNumberBoxModule,
  DxPopoverModule,
  DxPopupModule,
  DxScrollViewModule,
  DxSelectBoxModule,
  DxSwitchModule,
  DxTabPanelModule,
  DxTabsModule,
  DxTagBoxModule,
  DxTemplateModule,
  DxTextAreaModule,
  DxTextBoxModule,
  DxToastModule,
  DxTooltipModule
} from "devextreme-angular";
import {HTTP_INTERCEPTORS, HttpBackend, HttpClient, HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {CompaniesListComponent} from "./company/companies-list/companies-list.component";
import { CompanyComponent } from './company/company/company.component';
import { JobsListComponent } from './job/jobs-list/jobs-list.component';
import { JobComponent } from './job/job/job.component';
import {ContextService} from "./services/context.service";
import {CidAndJWTInterceptor, ErrorCatchingInterceptor} from "./services/interceptors";
import {AuthClientConfig, AuthConfig, AuthHttpInterceptor, AuthModule} from "@auth0/auth0-angular";
import {AuthzService} from "./services/authz.service";
@NgModule({
  declarations: [
    AppComponent,
    CompaniesListComponent,
    CompanyComponent,
    JobsListComponent,
    JobComponent

  ],
  imports: [
    BrowserModule,
    DxAutocompleteModule,
    DxChartModule,
    DxPopupModule,
    DxPopoverModule,
    DxSwitchModule,
    DxButtonModule,
    DxBoxModule,
    DxNumberBoxModule,
    DxTabsModule,
    DxTemplateModule,
    DxTextBoxModule,
    DxSelectBoxModule,
    DxCheckBoxModule,
    DxLoadPanelModule,
    DxListModule,
    DxLookupModule,
    DxFormModule,
    DxDropDownBoxModule,
    DxLoadIndicatorModule,
    DxDateBoxModule,
    DxDropDownButtonModule,
    DxCheckBoxModule,
    DxTabPanelModule,
    DxTagBoxModule,
    DxToastModule,
    DxScrollViewModule,
    DxCalendarModule,
    DxTextAreaModule,
    HttpClientModule,
    AppRoutingModule,
    fssSvcApiModule,
    FormsModule,
    DxDataGridModule,
    AuthModule.forRoot(),
  ],
  providers: [
    {
      provide: fssSvcConfiguration,
      useFactory: () => new fssSvcConfiguration(
        {
          basePath: "/fss-svc/api",
        }
      ),
      deps: [],
      multi: false
    },
    {
      provide: APP_INITIALIZER,
      useFactory: loadConfig,
      multi: true,
      deps: [HttpBackend, AuthClientConfig]
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthHttpInterceptor,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: CidAndJWTInterceptor,
      multi: true,
      deps: [AuthzService]
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorCatchingInterceptor,
      multi: true,
      deps: [ContextService]
    },

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

function loadConfig(handler: HttpBackend, auth0config: AuthClientConfig) {

  // note: this could be a remote url, for example service endpoint can
  // return the config
  let url = "/assets/config.json";

  return () => new HttpClient(handler).get(url).toPromise().then(
    (data: any) => {
      console.info("got:" + JSON.stringify(data))

      const cfg: AuthConfig = {

        authorizationParams: {
          audience: data.auth0ApiAudience,
          redirect_uri: window.location.origin,
          scope: 'openid profile email',
        },
        domain: data.auth0Domain,
        clientId: data.auth0ClientId,
        httpInterceptor: {
          allowedList: [
            '/api/*',
            '/fss-svc/api/*',
          ]
        }
      }
      auth0config.set(cfg)
    }
  );
}
