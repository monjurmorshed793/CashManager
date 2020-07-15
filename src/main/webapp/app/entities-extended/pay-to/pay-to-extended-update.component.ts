import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { PayToExtendedService } from './pay-to-extended.service';
import { PayToUpdateComponent } from '../../entities/pay-to/pay-to-update.component';

@Component({
  selector: 'jhi-pay-to-update',
  templateUrl: './pay-to-extended-update.component.html',
})
export class PayToExtendedUpdateComponent extends PayToUpdateComponent implements OnInit {
  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected payToService: PayToExtendedService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {
    super(dataUtils, eventManager, payToService, activatedRoute, fb);
  }
}
