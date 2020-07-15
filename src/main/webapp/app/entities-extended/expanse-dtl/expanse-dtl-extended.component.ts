import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ExpanseDtlExtendedService } from './expanse-dtl-extended.service';
import { ExpanseDtlExtendedDeleteDialogComponent } from './expanse-dtl-extended-delete-dialog.component';
import { ExpanseDtlComponent } from '../../entities/expanse-dtl/expanse-dtl.component';

@Component({
  selector: 'jhi-expanse-dtl',
  templateUrl: './expanse-dtl-extended.component.html',
})
export class ExpanseDtlExtendedComponent extends ExpanseDtlComponent implements OnInit, OnDestroy {
  constructor(
    protected expanseDtlService: ExpanseDtlExtendedService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    super(expanseDtlService, activatedRoute, router, eventManager, modalService);
  }
}
