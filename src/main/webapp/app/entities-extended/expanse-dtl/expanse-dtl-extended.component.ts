import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ExpanseDtlExtendedService } from './expanse-dtl-extended.service';
import { ExpanseDtlExtendedDeleteDialogComponent } from './expanse-dtl-extended-delete-dialog.component';
import { ExpanseDtlComponent } from '../../entities/expanse-dtl/expanse-dtl.component';
import { IExpanse } from '../../shared/model/expanse.model';
import { IExpanseDtl } from 'app/shared/model/expanse-dtl.model';

@Component({
  selector: 'jhi-expanse-dtl-extended',
  templateUrl: './expanse-dtl-extended.component.html',
})
export class ExpanseDtlExtendedComponent extends ExpanseDtlComponent implements OnInit, OnDestroy {
  @Input()
  expanseId: number | null = null;
  constructor(
    protected expanseDtlService: ExpanseDtlExtendedService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    super(expanseDtlService, activatedRoute, router, eventManager, modalService);
  }

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;
    if (this.expanseId) {
      this.expanseDtlService
        .query({
          'expanseId.equals': this.expanseId,
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        })
        .subscribe(
          (res: HttpResponse<IExpanseDtl[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else {
      this.expanseDtlService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        })
        .subscribe(
          (res: HttpResponse<IExpanseDtl[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    }
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInExpanseDtls();
  }

  addNew(): void {
    this.router.navigate(['/expanse-dtl', this.expanseId, 'new']);
  }
}
