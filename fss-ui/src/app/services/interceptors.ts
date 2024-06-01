import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {UUID} from "angular2-uuid";
import {Injectable} from "@angular/core";
import {catchError} from "rxjs/operators";
import {ContextService} from "./context.service";
import {AuthzService} from "./authz.service";

export class CidAndJWTInterceptor implements HttpInterceptor {

  constructor(private autzSvc:AuthzService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const cid = UUID.UUID();
    let updatedHeaders = req.headers.set('cid', cid);
    //todo:limit to valid urls
    if( this.autzSvc.isAuthenticated ){
      const jwt = this.autzSvc.token$.value;
      updatedHeaders = updatedHeaders.set('Authorization', 'Bearer ' + jwt)
      const impersonateUserID = localStorage.getItem('impersonate_user_id')
      if( impersonateUserID ){
        updatedHeaders = updatedHeaders.set('impersonate_user_id', impersonateUserID)
      }
      // console.log("added auth header: " + storage?.__raw)
    }
    const modifiedReq = req.clone({headers: updatedHeaders});
    return next.handle(modifiedReq);
  }
}

@Injectable()
export class ErrorCatchingInterceptor implements HttpInterceptor {

  constructor(protected cxtSvc: ContextService ) {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          let errorMsg = '';
          if( error.error && typeof error.error === 'string') {
            errorMsg = `Error: ${error.error}`;
          }else {
            if (error.error?.description) {
              errorMsg = `Error: ${error.error?.description}`;
            } else {
              errorMsg = `Error Code: ${error.status},  Message: ${error.message}`;
            }
          }
          console.error( JSON.stringify(error));
          this.cxtSvc.errorAlert(errorMsg)
          return throwError(error);
        })
      )
  }
}
