import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";


export interface Alert {
  timeout: number;
  type: string;
  msg: string;
  visible: boolean;

}

@Injectable({
  providedIn: 'root'
})
export class ContextService {

  public textCriteria = "";
  public quantity = 0;
  public selectedItems: Array<any> = [];
  public currentAccount$ = new BehaviorSubject('my');


  public alerts$ = new BehaviorSubject<Alert | undefined>(undefined);

  public operationInProgress = false;

  constructor() {
    let tc = localStorage.getItem("textCriteria")
    if (tc) {
      this.textCriteria = tc;
    }
    let si = localStorage.getItem("selectedItems")
    if (si) {
      this.selectedItems = JSON.parse(si);
    }
    let q = localStorage.getItem("quantity")
    if (q) {
      this.quantity = JSON.parse(q);
    }
  }


  store() {
    localStorage.setItem("textCriteria", this.textCriteria)
    localStorage.setItem("selectedItems", JSON.stringify(this.selectedItems))
    localStorage.setItem("quantity", JSON.stringify(this.quantity))
  }

  selectedItemsKeys(): Array<any> {
    return this.selectedItems.map(v => v.id)
  }

  removeSelectedByID(id: any) {
    let idx = this.selectedItems.findIndex(i => i.id == id)
    if (idx >= 0) {
      this.selectedItems.splice(idx, 1);
    }
  }

  infoAlert(msg: string) {
    this.publishAlert({
      type: 'success',
      msg: msg,
      timeout: 5000,
      visible: true
    })
  }

  storeAlert(v:Alert)  {
    const stored = this.listLatestAlerts()
    stored.push({...v})
    if (stored.length > 100) {
      stored.shift();
    }
    localStorage.setItem("latestAlerts", JSON.stringify(stored))
  }

  listLatestAlerts(): Array<Alert> {
    const storedAlerts = localStorage.getItem("latestAlerts")
    if (storedAlerts) {
      return JSON.parse(storedAlerts)
    }else {
      return []
    }
  }

  publishAlert(alert: Alert) {
    this.storeAlert(alert)
    this.alerts$.next(alert)
  }


  warningAlert(msg: string) {
    this.publishAlert({
      type: 'warning',
      msg: msg,
      timeout: 5000,
      visible: true
    })
  }

  errorAlert(msg: string | any) {
    let m = msg
    if (typeof msg != "string") {
      m = JSON.stringify(msg)
    }
    this.publishAlert({
      type: 'error',
      msg: m,
      timeout: 5000,
      visible: true
    })
  }

  getMergingMemberIDs(): Array<string> {
    const current = localStorage.getItem("mergingMemberIDs")
    if (current) {
      return JSON.parse(current)
    }
    return []
  }

  addToMerging(memberId: string) {
    const ids = this.getMergingMemberIDs()
    ids.push(memberId)
    this.setMergingMemberIDs(ids)
  }

  clearMerging() {
    localStorage.removeItem("mergingMemberIDs")
  }

  isMerging(id: string) {
    return this.getMergingMemberIDs().filter(i => i == id).length > 0;
  }

  setMergingMemberIDs(ids: Array<string>) {
    localStorage.setItem("mergingMemberIDs", JSON.stringify(ids))
  }
}
