import {Resource} from 'angular4-hal';

interface Passport {
  id: number;
  passportNumber: string;
  firstName: string;
  lastName: string;
  dateOfBirth: string;
  placeOfBirth: string;
  gender: string;
  expiryDate: string;
}
export interface AccountResource extends Resource {
  id: number;
  passport: Passport;
}
