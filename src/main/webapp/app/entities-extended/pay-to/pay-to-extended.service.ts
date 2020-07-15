import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { PayToService } from '../../entities/pay-to/pay-to.service';
import { IPayTo } from '../../shared/model/pay-to.model';
import { SERVER_API_URL } from '../../app.constants';

type EntityResponseType = HttpResponse<IPayTo>;
type EntityArrayResponseType = HttpResponse<IPayTo[]>;

@Injectable({ providedIn: 'root' })
export class PayToExtendedService extends PayToService {
  public resourceUrl = SERVER_API_URL + 'api/pay-tos';

  constructor(protected http: HttpClient) {
    super(http);
  }
}
