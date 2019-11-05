import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl} from '@angular/forms';
// tslint:disable-next-line:no-duplicate-imports
// @ts-ignore
import * as _moment from 'moment';
// @ts-ignore
import {default as _rollupMoment} from 'moment';
import {MatDatepickerInputEvent} from '@angular/material';

const moment = _rollupMoment || _moment;

export const MY_FORMATS = {
  parse: {
    dateInput: 'MM/YYYY',
  },
  display: {
    dateInput: 'MM/YYYY',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY',
  },
};

@Component({
  selector: 'app-date-selector',
  templateUrl: './date-selector.component.html',
  styleUrls: ['./date-selector.component.scss']
})
export class DateSelectorComponent implements OnInit {
  @Input() private dateTitle: string;
  private dateControl = new FormControl(moment().format('DD/MM/YYYY'));
  @Output() private forwardDate = new EventEmitter<string>();
  @Output() private returnDate = new EventEmitter<string>();

  constructor() {
  }

  ngOnInit() {
  }

  private dateToday(): Date {
    return new Date();
  }

  setDate($event: MatDatepickerInputEvent<any & Date>) {
    this.forwardDate.emit(moment($event.value).format('YYYY-MM-DD'));
    this.returnDate.emit(moment($event.value).format('YYYY-MM-DD'));
  }
}
