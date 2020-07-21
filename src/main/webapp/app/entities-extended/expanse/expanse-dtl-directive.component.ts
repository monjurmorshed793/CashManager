import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ExpanseDtlComponent } from '../../entities/expanse-dtl/expanse-dtl.component';
import { IExpanse } from '../../shared/model/expanse.model';
import { ExpanseDtlExtendedComponent } from '../expanse-dtl/expanse-dtl-extended.component';
import { ExpanseDtlExtendedService } from '../expanse-dtl/expanse-dtl-extended.service';
import { ExpanseExtendedService } from './expanse-extended.service';

@Component({
  selector: 'jhi-expanse-dtl-directive',
  templateUrl: './expanse-dtl-directive.component.html',
})
export class ExpanseDtlDirectiveComponent extends ExpanseDtlExtendedComponent implements OnInit, OnDestroy {
  constructor(
    protected expanseDtlService: ExpanseDtlExtendedService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected expanseService: ExpanseExtendedService
  ) {
    super(expanseDtlService, activatedRoute, router, eventManager, modalService, expanseService);
  }
}
