import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RegisterNewUserModel} from "../models/register-new-user.model";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class RegisterUserService {

  constructor(private httpClient: HttpClient) { }


  register(model: RegisterNewUserModel): Observable<any> {
    return this.httpClient.post('http://localhost:8080/api/users/register', model);
  }

}
