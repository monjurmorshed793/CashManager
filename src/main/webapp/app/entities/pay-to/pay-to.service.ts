import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPayTo } from 'app/shared/model/pay-to.model';

type EntityResponseType = HttpResponse<IPayTo>;
type EntityArrayResponseType = HttpResponse<IPayTo[]>;

@Injectable({ providedIn: 'root' })
export class PayToService {
  public resourceUrl = SERVER_API_URL + 'api/pay-tos';

  constructor(protected http: HttpClient) {}

  create(payTo: IPayTo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(payTo);
    return this.http
      .post<IPayTo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(payTo: IPayTo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(payTo);
    return this.http
      .put<IPayTo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPayTo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPayTo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(payTo: IPayTo): IPayTo {
    const copy: IPayTo = Object.assign({}, payTo, {
      createdOn: payTo.createdOn && payTo.createdOn.isValid() ? payTo.createdOn.toJSON() : undefined,
      modifiedOn: payTo.modifiedOn && payTo.modifiedOn.isValid() ? payTo.modifiedOn.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdOn = res.body.createdOn ? moment(res.body.createdOn) : undefined;
      res.body.modifiedOn = res.body.modifiedOn ? moment(res.body.modifiedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((payTo: IPayTo) => {
        payTo.createdOn = payTo.createdOn ? moment(payTo.createdOn) : undefined;
        payTo.modifiedOn = payTo.modifiedOn ? moment(payTo.modifiedOn) : undefined;
      });
    }
    return res;
  }
}
