import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DepositService } from '../../entities/deposit/deposit.service';
import { IDeposit } from '../../shared/model/deposit.model';
import { SERVER_API_URL } from '../../app.constants';

type EntityResponseType = HttpResponse<IDeposit>;
type EntityArrayResponseType = HttpResponse<IDeposit[]>;

@Injectable({ providedIn: 'root' })
export class DepositExtendedService extends DepositService {
  constructor(protected http: HttpClient) {
    super(http);
  }
}
