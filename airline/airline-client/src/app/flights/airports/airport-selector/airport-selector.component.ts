import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl} from '@angular/forms';
import {FlightService} from '../../../services/flight.service';
import {Observable, of} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import {MatAutocompleteSelectedEvent} from '@angular/material';

@Component({
  selector: 'app-airport-selector',
  templateUrl: './airport-selector.component.html',
  styleUrls: ['./airport-selector.component.scss']
})
export class AirportSelectorComponent implements OnInit {

  @Input() formLabel: string;
  private airportNames: string[] = [];
  private options: Observable<string[]> = of([]);
  airportNameControl = new FormControl();
  @Input() pairedAirportNameControl: FormControl;
  @Output() selectedOption = new EventEmitter<string>();

  constructor(private flightService: FlightService) {
  }

  ngOnInit() {
    this.setAirportNames();
  }

  private setAirportNames() {
    this.flightService.getAirportNames().subscribe(names => {
      this.airportNames = names;
      this.filterOptions();
    });
  }

  private filterOptions() {
    this.options = this.airportNameControl.valueChanges.pipe(
      startWith(''),
      map(value => this.processFilter(value))
    );
  }

  private processFilter(value: string) {
    const filterValue = value.toLowerCase();
    const pairedValue = (this.pairedAirportNameControl.value != null) ? this.pairedAirportNameControl.value : '';
    return this.airportNames.filter(option => option.toLowerCase() !== pairedValue.toLowerCase() && option.toLowerCase().includes(filterValue));
  }

  sendSelection($event: MatAutocompleteSelectedEvent) {
    this.selectedOption.emit($event.option.value);
  }
}
