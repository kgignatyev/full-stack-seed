import {Component, OnInit} from '@angular/core';
import {AutomationServiceV1Service, V1StartWorkflowRequest, V1WorkflowInfo} from "../../generated/api_client";

@Component({
  selector: 'app-workflows',
  standalone: false,
  templateUrl: './workflows.html'
})
export class Workflows implements OnInit {
  workflows: V1WorkflowInfo[] = [];

  constructor(private automationService: AutomationServiceV1Service) {
  }

  ngOnInit(): void {
    this.refresh();
  }

  refresh() {
    this.automationService.listWorkflows().subscribe(
      (data: V1WorkflowInfo[]) => {
        this.workflows = data;
      }
    );
  }

  startLeadsAcquisition() {
    let request:V1StartWorkflowRequest = {
      wfType: 'LeadsAcquisitionWorkflow'
    };
    this.automationService.startWorkflow( request).subscribe( d => this.refresh())
  }
}
