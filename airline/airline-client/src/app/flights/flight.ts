export class Flight {

  private departureAirportName: string;
  private arrivalAirportName: string;
  private departureDate: string;
  private returnDate: string;

  constructor() {
  }

  setDepartureAirportName(departureAirportName: string) {
    this.departureAirportName = departureAirportName;
  }

  setArrivalAirportName(arrivalAirportName: string) {
    this.arrivalAirportName = arrivalAirportName;
  }

  setDepartureDate(departureDate: string) {
    this.departureDate = departureDate;
  }

  setReturnDate(returnDate: string) {
    this.returnDate = returnDate;
  }

  get DepartureAirportName() {
    return this.departureAirportName;
  }

  get ArrivalAirportName() {
    return this.arrivalAirportName;
  }

  get DepartureDate() {
    return this.departureDate;
  }

  get ReturnDate() {
    return this.returnDate;
  }

  isSingleFlightValid() {
    return this.departureAirportName != null && this.arrivalAirportName != null && this.departureDate != null;
  }

  isReturnFlightValid() {
    return this.isSingleFlightValid() && this.returnDate != null;
  }
}
