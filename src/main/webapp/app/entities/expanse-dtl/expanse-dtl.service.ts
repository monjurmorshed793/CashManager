import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IExpanseDtl } from 'app/shared/model/expanse-dtl.model';

type EntityResponseType = HttpResponse<IExpanseDtl>;
type EntityArrayResponseType = HttpResponse<IExpanseDtl[]>;

@Injectable({ providedIn: 'root' })
export class ExpanseDtlService {
  public resourceUrl = SERVER_API_URL + 'api/expanse-dtls';

  constructor(protected http: HttpClient) {}

  create(expanseDtl: IExpanseDtl): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(expanseDtl);
    return this.http
      .post<IExpanseDtl>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(expanseDtl: IExpanseDtl): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(expanseDtl);
    return this.http
      .put<IExpanseDtl>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IExpanseDtl>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IExpanseDtl[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(expanseDtl: IExpanseDtl): IExpanseDtl {
    const copy: IExpanseDtl = Object.assign({}, expanseDtl, {
      createdOn: expanseDtl.createdOn && expanseDtl.createdOn.isValid() ? expanseDtl.createdOn.toJSON() : undefined,
      modifiedOn: expanseDtl.modifiedOn && expanseDtl.modifiedOn.isValid() ? expanseDtl.modifiedOn.toJSON() : undefined,
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
      res.body.forEach((expanseDtl: IExpanseDtl) => {
        expanseDtl.createdOn = expanseDtl.createdOn ? moment(expanseDtl.createdOn) : undefined;
        expanseDtl.modifiedOn = expanseDtl.modifiedOn ? moment(expanseDtl.modifiedOn) : undefined;
      });
    }
    return res;
  }
}
