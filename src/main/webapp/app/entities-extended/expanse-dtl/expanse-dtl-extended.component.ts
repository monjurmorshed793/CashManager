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
import { ExpanseExtendedService } from 'app/entities-extended/expanse/expanse-extended.service';

@Component({
  selector: 'jhi-expanse-dtl-extended',
  templateUrl: './expanse-dtl-extended.component.html',
})
export class ExpanseDtlExtendedComponent extends ExpanseDtlComponent implements OnInit, OnDestroy {
  @Input()
  expanseId: number | null = null;
  expanse: IExpanse | null = null;

  constructor(
    protected expanseDtlService: ExpanseDtlExtendedService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected expanseService: ExpanseExtendedService
  ) {
    super(expanseDtlService, activatedRoute, router, eventManager, modalService);
  }

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad = 1;
    if (this.expanseId) {
      this.expanseDtlService
        .query({
          'expanseId.equals': this.expanseId,
          page: pageToLoad - 1,
          size: 100,
          sort: ['id,asc'],
        })
        .subscribe(
          (res: HttpResponse<IExpanseDtl[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );

      this.expanseService.find(this.expanseId).subscribe(res => {
        this.expanse = res.body;
      });
    } else {
      this.expanseDtlService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: ['id,asc'],
        })
        .subscribe(
          (res: HttpResponse<IExpanseDtl[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    }
  }

  ngOnInit(): void {
    //this.expanseService.getExpanseId().subscribe((a)=> this.expanseId = a);
    this.handleNavigation();
    this.loadPage();
    this.registerChangeInExpanseDtls();
  }

  addNew(): void {
    this.router.navigate(['/expanse-dtl', this.expanseId, 'new']);
  }

  registerChangeInExpanseDtls(): void {
    this.eventSubscriber = this.eventManager.subscribe('expanseDtlListModification', () => {
      this.loadPage();
    });
  }

  protected onSuccess(data: IExpanseDtl[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.expanseDtls = data || [];
    this.ngbPaginationPage = this.page;
  }

  delete(expanseDtl: IExpanseDtl): void {
    const modalRef = this.modalService.open(ExpanseDtlExtendedDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.expanseDtl = expanseDtl;
  }
}
