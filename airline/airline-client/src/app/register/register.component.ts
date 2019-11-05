import {Component, OnInit} from '@angular/core';
import {BookingOrderService} from '../booking-order/booking-order.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {MatHorizontalStepper} from '@angular/material';
import {RegistrationService} from './registration.service';
import {Router} from '@angular/router';
import {RegistrationDetails} from './registration-details';
import * as moment from 'moment';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  private userExists = false;
  private validators: Validators = [Validators.required, Validators.minLength(3), Validators.maxLength(30)];

  private user = new FormGroup({
    username: new FormControl('', this.validators),
    password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.maxLength(50)]),
    confirmPassword: new FormControl('', [Validators.required, Validators.minLength(8), Validators.maxLength(50)])
  });
  private passport = new FormGroup({
    number: new FormControl('', Validators.required),
    firstName: new FormControl('', this.validators),
    lastName: new FormControl('', this.validators),
    dateOfBirth: new FormControl('', Validators.required),
    placeOfBirth: new FormControl('', Validators.required),
    gender: new FormControl('', Validators.required),
    expiryDate: new FormControl('', Validators.required),
  });
  private paymentCard = new FormGroup({
    name: new FormControl('', this.validators),
    number: new FormControl('', [Validators.required, Validators.minLength(13), Validators.pattern('^[0-9]*$')]),
    expiryDate: new FormControl('', Validators.required)
  });

  constructor(private bookingService: BookingOrderService, private registrationService: RegistrationService, private router: Router) {
  }

  ngOnInit() {
  }

  private dateToday() {
    return new Date();
  }

  processRegistration() {
    const registrationDetails = this.getRegistrationDetails();
    this.registrationService.registerClient(registrationDetails).subscribe(value => {
      this.router.navigateByUrl('login');
    }, error => {
      console.log(error);
    });
  }

  checkLoginDetails(stepper: MatHorizontalStepper) {
    const password = this.user.get('password').value;
    const confirmedPassword = this.user.get('confirmPassword').value;
    if (password === confirmedPassword) {
      this.checkUserExists(stepper);
    }
  }

  getGenders() {
    return ['MALE', 'FEMALE', 'OTHER'];
  }

  private checkUserExists(stepper) {
    this.registrationService.checkUserExists(this.user.get('username').value).subscribe(
      value => {
        if (value !== true) {
          this.userExists = false;
          stepper.next();
        } else {
          this.userExists = true;
        }
      }
    );
  }

  returnToLogin() {
    this.router.navigateByUrl('login');
  }

  private getPassportData(fieldName) {
    return this.passport.get(fieldName).value;
  }

  private getUserData(fieldName) {
    return this.user.get(fieldName).value;
  }

  private getPaymentCardData(fieldName) {
    return this.paymentCard.get(fieldName).value;
  }

  private getRegistrationDetails(): RegistrationDetails {
    return {
      username: this.getUserData('username'),
      password: this.getUserData('password'),
      firstName: this.getPassportData('firstName'),
      lastName: this.getPassportData('lastName'),
      dateOfBirth: this.parseDate(this.getPassportData('dateOfBirth')),
      placeOfBirth: this.getPassportData('placeOfBirth'),
      gender: this.getPassportData('gender'),
      passportNumber: this.getPassportData('number'),
      passportExpiryDate: this.parseDate(this.getPassportData('expiryDate')),
      paymentCardNumber: this.getPaymentCardData('number'),
      paymentCardName: this.getPaymentCardData('name'),
      paymentCardExpiryDate: this.parseDate(this.getPaymentCardData('expiryDate'))
    };
  }

  private parseDate(date) {
    return moment(date).format('YYYY-MM-DD');
  }
}
