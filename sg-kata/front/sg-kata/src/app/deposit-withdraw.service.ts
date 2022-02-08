import {Inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DepositWithdrawService {

  constructor(@Inject('API_BASE_URL') private baseUrl: string,
              private http: HttpClient,) {
  }

  public deposit(iban: string, amount: number): Observable<string>  {
    return this.operation(iban, amount, 'deposit');
  }

  public withdraw(iban: string, amount: number): Observable<string>  {
    return this.operation(iban, amount, 'withdraw');
  }

  private operation(iban: string, amount: number, operation: 'deposit'|'withdraw'): Observable<string>  {
    return this.http.post(
      `${this.baseUrl}/accounts/${iban}/${operation}?amount=${amount}&reference=ref`,
      {},
      {responseType: "text"});
  }
}
