import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IExpanse } from 'app/shared/model/expanse.model';

type EntityResponseType = HttpResponse<IExpanse>;
type EntityArrayResponseType = HttpResponse<IExpanse[]>;

@Injectable({ providedIn: 'root' })
export class ExpanseService {
  public resourceUrl = SERVER_API_URL + 'api/expanses';

  constructor(protected http: HttpClient) {}

  create(expanse: IExpanse): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(expanse);
    return this.http
      .post<IExpanse>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(expanse: IExpanse): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(expanse);
    return this.http
      .put<IExpanse>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IExpanse>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IExpanse[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(expanse: IExpanse): IExpanse {
    const copy: IExpanse = Object.assign({}, expanse, {
      voucherDate: expanse.voucherDate && expanse.voucherDate.isValid() ? expanse.voucherDate.format(DATE_FORMAT) : undefined,
      postDate: expanse.postDate && expanse.postDate.isValid() ? expanse.postDate.toJSON() : undefined,
      createdOn: expanse.createdOn && expanse.createdOn.isValid() ? expanse.createdOn.toJSON() : undefined,
      modifiedOn: expanse.modifiedOn && expanse.modifiedOn.isValid() ? expanse.modifiedOn.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.voucherDate = res.body.voucherDate ? moment(res.body.voucherDate) : undefined;
      res.body.postDate = res.body.postDate ? moment(res.body.postDate) : undefined;
      res.body.createdOn = res.body.createdOn ? moment(res.body.createdOn) : undefined;
      res.body.modifiedOn = res.body.modifiedOn ? moment(res.body.modifiedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((expanse: IExpanse) => {
        expanse.voucherDate = expanse.voucherDate ? moment(expanse.voucherDate) : undefined;
        expanse.postDate = expanse.postDate ? moment(expanse.postDate) : undefined;
        expanse.createdOn = expanse.createdOn ? moment(expanse.createdOn) : undefined;
        expanse.modifiedOn = expanse.modifiedOn ? moment(expanse.modifiedOn) : undefined;
      });
    }
    return res;
  }
}
