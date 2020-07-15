import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ExpanseExtendedService } from './expanse-extended.service';
import { ExpanseUpdateComponent } from '../../entities/expanse/expanse-update.component';
import { PayToService } from '../pay-to/pay-to.service';

@Component({
  selector: 'jhi-expanse-update',
  templateUrl: './expanse-extended-update.component.html',
})
export class ExpanseExtendedUpdateComponent extends ExpanseUpdateComponent implements OnInit {
  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected expanseService: ExpanseExtendedService,
    protected payToService: PayToService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {
    super(dataUtils, eventManager, expanseService, payToService, activatedRoute, fb);
  }
}
