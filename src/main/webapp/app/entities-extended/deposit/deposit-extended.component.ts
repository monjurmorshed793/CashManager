import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDeposit } from 'app/shared/model/deposit.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DepositExtendedService } from './deposit-extended.service';
import { DepositExtendedDeleteDialogComponent } from './deposit-extended-delete-dialog.component';
import { DepositComponent } from '../../entities/deposit/deposit.component';

@Component({
  selector: 'jhi-deposit',
  templateUrl: './deposit-extended.component.html',
})
export class DepositExtendedComponent extends DepositComponent implements OnInit, OnDestroy {
  constructor(
    protected depositService: DepositExtendedService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    super(depositService, activatedRoute, router, eventManager, modalService);
  }
}
