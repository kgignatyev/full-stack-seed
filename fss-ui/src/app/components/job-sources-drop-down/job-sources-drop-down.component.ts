import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-job-sources-drop-down',
  standalone: false,
  templateUrl: './job-sources-drop-down.component.html'
})
export class JobSourcesDropDownComponent {

  jobSources: string[] = [
    '',
    'LinkedIn',
    'Indeed',
    'Company Website',
    'Referral',
    'Recruiter',
    'Job Fair',
    'Networking Event',
    'Social Media',
    'Other'
  ];

  @Input() jobSource: string = '';
  @Output() jobSourceChange = new EventEmitter<string>();
  isDropDownBoxOpened = false;

  onJobSourceChange(event: any) {
    console.info("onJobSourceChange", event);
    this.jobSourceChange.emit(event.selectedItem);
    this.isDropDownBoxOpened = false;
  }

}
