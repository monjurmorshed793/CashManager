import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { ExpanseDtlUpdateComponent } from '../../entities/expanse-dtl/expanse-dtl-update.component';
import { ExpanseDtlExtendedService } from './expanse-dtl-extended.service';
import { ExpanseService } from '../../entities/expanse/expanse.service';
import { IItem } from '../../shared/model/item.model';
import { IExpanse } from '../../shared/model/expanse.model';
import { ItemExtendedService } from '../item/item-extended.service';

type SelectableEntity = IExpanse | IItem;

@Component({
  selector: 'jhi-expanse-dtl-update',
  templateUrl: './expanse-dtl-extended-update.component.html',
})
export class ExpanseDtlExtendedUpdateComponent extends ExpanseDtlUpdateComponent implements OnInit {
  editForm = this.fb.group({
    id: [],
    quantity: [null, [Validators.required]],
    unitPrice: [null, [Validators.required]],
    amount: [null],
    createdBy: [],
    createdOn: [],
    modifiedBy: [],
    modifiedOn: [],
    expanseId: [],
    itemId: [null, Validators.required],
  });

  constructor(
    protected expanseDtlService: ExpanseDtlExtendedService,
    protected expanseService: ExpanseService,
    protected itemService: ItemExtendedService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {
    super(expanseDtlService, expanseService, itemService, activatedRoute, fb);
  }
}
