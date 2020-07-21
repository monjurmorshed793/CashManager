import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ExpanseExtendedService } from './expanse-extended.service';
import { ExpanseExtendedDeleteDialogComponent } from './expanse-extended-delete-dialog.component';
import { ExpanseComponent } from '../../entities/expanse/expanse.component';
import { IExpanse } from '../../shared/model/expanse.model';
import { Account } from '../../core/user/account.model';
import { AccountService } from '../../core/auth/account.service';
import { UserService } from '../../core/user/user.service';
import { ItemService } from '../../entities/item/item.service';
import { PayToService } from '../../entities/pay-to/pay-to.service';
import { IUser } from '../../core/user/user.model';
import { IPayTo } from '../../shared/model/pay-to.model';
import { Item } from '../../shared/model/item.model';

@Component({
  selector: 'jhi-expanse',
  templateUrl: './expanse-extended.component.html',
})
export class ExpanseExtendedComponent extends ExpanseComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  newId: number | null = null;
  showLoader = false;
  login: string | null = null;
  loginList: string[] = [];
  payToId: number | null = null;
  payToLists: IPayTo[] = [];
  itemId: number | null = null;
  itemLists: Item[] = [];

  constructor(
    protected expanseService: ExpanseExtendedService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private accountService: AccountService,
    private userService: UserService,
    private itemService: ItemService,
    private payToService: PayToService
  ) {
    super(expanseService, activatedRoute, dataUtils, router, eventManager, modalService);
  }

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    if (this.account?.authorities.includes('ROLE_ADMIN')) {
      this.expanseService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: ['id,desc'],
        })
        .subscribe(
          (res: HttpResponse<IExpanse[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else {
      this.expanseService
        .query({
          'createdBy.equals': this.account?.login,
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: ['id,desc'],
        })
        .subscribe(
          (res: HttpResponse<IExpanse[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    }
  }

  ngOnInit(): void {
    this.showLoader = true;
    this.expanseService.setExpanseId(null);
    this.expanseService.getNewId().subscribe(res => {
      this.newId = res;
      if (res) {
        const id = res;
        // this.expanseService.setNewId(null);
        this.router.navigate(['/expanse', id, 'edit']);
      }
    });
    this.accountService.getAuthenticationState().subscribe(res => (this.account = res));
    this.handleNavigation();
    this.registerChangeInExpanses();
  }

  registerChangeInExpanses(): void {
    this.eventSubscriber = this.eventManager.subscribe('expanseListModification', () => {
      this.loadPage();
    });
  }

  protected onSuccess(data: IExpanse[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.showLoader = false;
    this.page = page;
    if (navigate) {
      this.router.navigate(['/expanse'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.expanses = data || [];
    this.ngbPaginationPage = this.page;
  }
}
