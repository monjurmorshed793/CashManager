import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { ExpanseDtlService } from '../../entities/expanse-dtl/expanse-dtl.service';
import { IExpanseDtl } from '../../shared/model/expanse-dtl.model';
import { SERVER_API_URL } from '../../app.constants';

type EntityResponseType = HttpResponse<IExpanseDtl>;
type EntityArrayResponseType = HttpResponse<IExpanseDtl[]>;

@Injectable({ providedIn: 'root' })
export class ExpanseDtlExtendedService extends ExpanseDtlService {
  public resourceUrl = SERVER_API_URL + 'api/expanse-dtls';

  constructor(protected http: HttpClient) {
    super(http);
  }
}
