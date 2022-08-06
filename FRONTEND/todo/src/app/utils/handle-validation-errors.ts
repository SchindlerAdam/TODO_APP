import {HttpErrorResponse} from "@angular/common/http";
import {FormGroup} from "@angular/forms";



export function handleValidationErrors(error: Error, form: FormGroup): void {
  if (error instanceof HttpErrorResponse && error.status === 400) {
    for (const backendError of error.error.fieldErrors) {
      const formControl = form.get(backendError.field);
      if (formControl) {
        formControl.setErrors({serverError: backendError.message})
      }
    }
  }
}
