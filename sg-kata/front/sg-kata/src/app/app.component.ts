import {Component, Inject} from '@angular/core';
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {Observable} from "rxjs";
import {DepositWithdrawService} from "./deposit-withdraw.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'sg-kata';
  iban = "FRMyIban";
  amount = 0;
  sanitizedUrl: SafeUrl;
  response$!: Observable<string>;

  constructor(@Inject('API_BASE_URL') baseUrl: string,
              private sanitizer:DomSanitizer,
              private depositWithdrawService: DepositWithdrawService) {
    this.sanitizedUrl = this.sanitizer.bypassSecurityTrustUrl(`${baseUrl}/accounts/${this.iban}/statement`);
  }

  withdraw(): void {
    this.response$ = this.depositWithdrawService.withdraw(this.iban, this.amount)
  }

  deposit() {
    this.response$ = this.depositWithdrawService.deposit(this.iban, this.amount)
  }
}
