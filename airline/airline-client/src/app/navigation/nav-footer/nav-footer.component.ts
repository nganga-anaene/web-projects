import { Component, OnInit } from '@angular/core';
import * as moment from 'moment';

@Component({
  selector: 'app-nav-footer',
  templateUrl: './nav-footer.component.html',
  styleUrls: ['./nav-footer.component.scss']
})
export class NavFooterComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  currentDate() {
    return moment(moment.now()).format('YYYY');
  }
}
