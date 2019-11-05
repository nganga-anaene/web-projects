import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, Subject} from 'rxjs';
import {AirportsResource} from '../resource-interfaces/airports-resource';
import {AirportResource} from '../resource-interfaces/airport-resource';
import {FlightsResource} from '../resource-interfaces/flights-resource';
import {FlightResource} from '../resource-interfaces/flight-resource';

@Injectable({
  providedIn: 'root'
})
export class FlightService {

  private airportsUrl = 'http://localhost:8080/airports';
  private airportResourcesSubject = new Subject<AirportResource[]>();
  private airportNamesSubject = new Subject<string[]>();
  private forwardFlights = new Subject<FlightResource[]>();
  private returnFlights = new Subject<FlightResource[]>();

  constructor(private http: HttpClient) {
  }

  // airport observables and subscriptions
  private sendAirportNames(airportNames: string[]) {
    this.airportNamesSubject.next(airportNames);
  }

  private sendAirportResources(airportResources: AirportResource[]) {
    this.airportResourcesSubject.next(airportResources);
  }

  getAirportResources(): Observable<AirportResource[]> {
    return this.airportResourcesSubject.asObservable();
  }

  getAirportNames(): Observable<string[]> {
    return this.airportNamesSubject.asObservable();
  }

  getAirportsResource(): Observable<AirportsResource> {
    return this.http.get<AirportsResource>(this.airportsUrl);
  }

  sendAirportSubscriptions(resource: AirportsResource) {
    const airportResources = resource._embedded.airportList;
    const airportNames = resource._embedded.airportList.map(airport => airport.name);
    this.sendAirportNames(airportNames);
    this.sendAirportResources(airportResources);
  }

  // flight observables and subscriptions
  getFlights(departureAirportId, arrivalAirportId, date): Observable<FlightsResource> {
    return this.http.get <FlightsResource>('http://localhost:8080/flights/departure/'
      + departureAirportId + '/arrival/' + arrivalAirportId + '?flight-date=' + date);
  }

  sendForwardFlightSubscriptions(resource: FlightsResource) {
    this.sendForwardFlightResources(resource._embedded.flightList);
  }

  private sendForwardFlightResources(flightList: FlightResource[]) {
    this.forwardFlights.next(flightList);
  }

  getForwardFlightResources(): Observable<FlightResource[]> {
    return this.forwardFlights.asObservable();
  }

  sendReturnFlightSubscriptions(returnResource: FlightsResource) {
    this.sendReturnFlightResources(returnResource._embedded.flightList);
  }

  sendEmptyReturnFlights() {
    this.returnFlights.next();
  }

  private sendReturnFlightResources(flightList: FlightResource[]) {
    return this.returnFlights.next(flightList);
  }

  getReturnFlightResources(): Observable<FlightResource[]> {
    return this.returnFlights.asObservable();
  }

  getFeaturedFlights() {
    return this.http.get<FlightsResource>('http://localhost:8080/flights/featured/');
  }
}
