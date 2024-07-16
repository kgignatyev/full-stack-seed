import {BehaviorSubject, Subscription} from "rxjs";
import {AuthService} from "@auth0/auth0-angular";
import {Router} from "@angular/router";
import {ContextService} from "./context.service";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class AuthzService {
  idToken$: BehaviorSubject<string|undefined> = new BehaviorSubject<string|undefined>("");
  public userAuth0$ = new BehaviorSubject<any>(null);
  // public hasAdminRole = false
  public isAuthenticated = false;

  private tokenClaimsSub: Subscription | undefined;
  private userSub: Subscription | undefined;


  constructor(public authService: AuthService, private router: Router,
              // private membershipSvc: MemberServiceV1Service,
              protected cxtSvc: ContextService) {


    this.userAuth0$.subscribe(u => {
      if (u) {
        this.isAuthenticated = true
      } else {
        this.isAuthenticated = false
      }
    })

    // this.hasAdminRole$.subscribe(d => this.hasAdminRole = d)
    authService.error$.subscribe(e => {
      console.error("auth error:" + JSON.stringify(e))
      //@ts-ignore
      this.cxtSvc.errorAlert("Authentication error:" + e.error_description)
    })

    authService.isAuthenticated$.subscribe(v => {
      this.isAuthenticated = v
      if (this.isAuthenticated) {
        this.tokenClaimsSub = authService.idTokenClaims$.subscribe(t => {
          console.info("token:" + JSON.stringify(t))
          if( t ){
            this.idToken$.next(t.__raw)
          }else {
            this.idToken$.next(undefined)
          }
        })
        this.userSub = authService.user$.subscribe(u => {
          console.info("auth0_user:" + JSON.stringify(u))
          this.userAuth0$.next(u)
        });
      } else {
        console.info("User logged out")
        if (this.tokenClaimsSub) {
          this.tokenClaimsSub.unsubscribe()
        }
        if (this.userSub) {
          this.userSub.unsubscribe()
        }
      }
    });

  }

  login() {
    this.authService.loginWithRedirect();
  }

  logout() {
    this.authService.logout({
      logoutParams: {
        returnTo: location.origin
      }

    });
  }
}
