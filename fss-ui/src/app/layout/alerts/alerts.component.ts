import { Component } from '@angular/core';
import {Alert, ContextService} from "../../services/context.service";
import notify from 'devextreme/ui/notify';
import hideToasts from 'devextreme/ui/toast/hide_toasts';

@Component({
    selector: 'app-alerts',
    templateUrl: './alerts.component.html',
    standalone: false
})
export class AlertsComponent {
  alerts: Alert[] = [];

  constructor( private  cxtSvc:ContextService) { }

  ngOnInit(): void {
    this.cxtSvc.alerts$.subscribe( a =>{
      if( a !== undefined) {
        this.show(a);
      }
    })
  }

  show(alert: Alert) {
    // const position: any = 'top center';
    const position: any = 'top left';
    const direction: any =  'down-push'

    notify({

        message: alert.msg,
        type: alert.type,
        displayTime: 10000,
        closeOnClick: true,
        animation: {
          show: {
            type: 'fade', duration: 400, from: 0, to: 1,
          },
          hide: { type: 'fade', duration: 40, to: 0 },
        },
      },
      { position, direction });

  }

  hideAll() {
    hideToasts();
  }
}
