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
import { AccountService } from '../../core/auth/account.service';
import { Account } from '../../core/user/account.model';

@Component({
  selector: 'jhi-deposit',
  templateUrl: './deposit-extended.component.html',
})
export class DepositExtendedComponent extends DepositComponent implements OnInit, OnDestroy {
  account: Account | null = null;

  constructor(
    protected depositService: DepositExtendedService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private accountService: AccountService
  ) {
    super(depositService, activatedRoute, router, eventManager, modalService);
  }

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    if (this.account?.authorities.includes('ROLE_ADMIN')) {
      this.depositService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: ['id,desc'],
        })
        .subscribe(
          (res: HttpResponse<IDeposit[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else {
      this.depositService
        .query({
          'createdBy.equals': this.account?.login,
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: ['id,desc'],
        })
        .subscribe(
          (res: HttpResponse<IDeposit[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    }
  }

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(res => {
      this.account = res;
    });
    this.handleNavigation();
    this.registerChangeInDeposits();
  }
}
