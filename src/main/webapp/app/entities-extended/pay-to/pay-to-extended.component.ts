import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { PayToExtendedService } from './pay-to-extended.service';
import { PayToExtendedDeleteDialogComponent } from './pay-to-extended-delete-dialog.component';
import { PayToComponent } from '../../entities/pay-to/pay-to.component';

@Component({
  selector: 'jhi-pay-to',
  templateUrl: './pay-to-extended.component.html',
})
export class PayToExtendedComponent extends PayToComponent implements OnInit, OnDestroy {
  constructor(
    protected payToService: PayToExtendedService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    super(payToService, activatedRoute, dataUtils, router, eventManager, modalService);
  }
}
