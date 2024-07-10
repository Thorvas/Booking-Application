import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export function maxTicketValidator(maxNumber: number): ValidatorFn {
    return (control: AbstractControl) : {[key: string]: any} | null => {
        const isValid = control.value < maxNumber;
        return isValid ? null : {'maxTicketValidator': { value: control.value}};
    }

}