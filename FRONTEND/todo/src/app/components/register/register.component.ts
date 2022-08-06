import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {RegisterNewUserModel} from "../../models/register-new-user.model";
import {RegisterUserService} from "../../services/register-user.service";
import {handleValidationErrors} from "../../utils/handle-validation-errors";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  passwordType: string = 'password';
  showPassword: boolean = false;
  userRole: string = 'USER';

  constructor(private formBuilder: FormBuilder, private registerUserService: RegisterUserService) { }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      userName: ['', [Validators.required, this.isUserNameValid.bind(this)]],
      userEmail: ['', [Validators.required,Validators.email, this.isEmailValid.bind(this)]],
      userPassword: ['', [Validators.required, this.isPasswordValid.bind(this)]]
    })
  }

  onChangeShowPassword() {
    this.showPassword = !this.showPassword;
    if (this.showPassword) {
      this.passwordType = 'text';
    } else {
      this.passwordType = 'password';
    }
  }

  onSubmit() {
    if (!this.registerForm.invalid) {
      const model: RegisterNewUserModel = {
        userName: this.registerForm.get('userName')?.value,
        userEmail: this.registerForm.get('userEmail')?.value,
        userPassword: this.registerForm.get('userPassword')?.value,
        userRole: this.userRole
      }
      this.registerUserService.register(model).subscribe(
        () => console.log('ASD'),
        error => handleValidationErrors(error, this.registerForm)
      );
    }
  }


  // FORM VALIDATION

  isUserNameValid(formControl: FormControl) {
    if (formControl.value === '' || formControl.value === null) {
      return {'emptyField': true};
    } else if (formControl.value.length < 3 || formControl.value.length > 10) {
      return {'emptyField': true};
    } else {
      return null;
    }
  }

  isEmailValid(formControl: FormControl) {
    if (formControl.value === '' || formControl.value === null) {
      return {'emptyField': true};
    }
    return null;
  }

  isPasswordValid(formControl: FormControl) {
    let numberChars = 0;

    for (let i = 0; i < formControl.value.length; i++) {
      if (!isNaN(formControl.value[i])) {
        numberChars++;
      }
    }

    if (formControl.value.length < 4 ) {
      return {'emptyField': true};
    }
    else if (numberChars < 3) {
      return {'emptyField': true};
    }
    else {
      return null;
    }
  }

/*  isPasswordContainsUppercaseChars(formControl: FormControl) {
    let upperChaseChars = 0;
    for(let i = 0; i < formControl.value.length; i++) {
      if(formControl.value[i] == formControl.value[i].toUpperCase()) {
        upperChaseChars++;
      }
    }
    if(upperChaseChars < 1) {
      return {'emptyField': true};
    } else {
      return null;
    }
  }*/


}
