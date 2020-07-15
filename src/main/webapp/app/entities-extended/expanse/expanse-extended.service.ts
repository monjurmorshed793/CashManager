import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { IExpanse } from '../../shared/model/expanse.model';
import { ExpanseService } from '../../entities/expanse/expanse.service';
import { SERVER_API_URL } from '../../app.constants';

type EntityResponseType = HttpResponse<IExpanse>;
type EntityArrayResponseType = HttpResponse<IExpanse[]>;

@Injectable({ providedIn: 'root' })
export class ExpanseExtendedService extends ExpanseService {
  public resourceUrl = SERVER_API_URL + 'api/expanses';

  constructor(protected http: HttpClient) {
    super(http);
  }
}