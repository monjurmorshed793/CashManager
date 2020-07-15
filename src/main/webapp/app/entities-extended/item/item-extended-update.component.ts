import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ItemExtendedService } from './item-extended.service';
import { ItemUpdateComponent } from '../../entities/item/item-update.component';

@Component({
  selector: 'jhi-item-update',
  templateUrl: './item-extended-update.component.html',
})
export class ItemExtendedUpdateComponent extends ItemUpdateComponent implements OnInit {
  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected itemService: ItemExtendedService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {
    super(dataUtils, eventManager, itemService, activatedRoute, fb);
  }
}
