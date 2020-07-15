import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { ItemService } from '../../entities/item/item.service';
import { IItem } from '../../shared/model/item.model';
import { SERVER_API_URL } from '../../app.constants';

type EntityResponseType = HttpResponse<IItem>;
type EntityArrayResponseType = HttpResponse<IItem[]>;

@Injectable({ providedIn: 'root' })
export class ItemExtendedService extends ItemService {
  public resourceUrl = SERVER_API_URL + 'api/items';

  constructor(protected http: HttpClient) {
    super(http);
  }
}
