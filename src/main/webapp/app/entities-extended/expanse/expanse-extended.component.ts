import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ExpanseExtendedService } from './expanse-extended.service';
import { ExpanseExtendedDeleteDialogComponent } from './expanse-extended-delete-dialog.component';
import { ExpanseComponent } from '../../entities/expanse/expanse.component';

@Component({
  selector: 'jhi-expanse',
  templateUrl: './expanse-extended.component.html',
})
export class ExpanseExtendedComponent extends ExpanseComponent implements OnInit, OnDestroy {
  constructor(
    protected expanseService: ExpanseExtendedService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    super(expanseService, activatedRoute, dataUtils, router, eventManager, modalService);
  }
}
