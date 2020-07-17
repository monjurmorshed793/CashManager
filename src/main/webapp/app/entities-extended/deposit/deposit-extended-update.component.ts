import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { DepositExtendedService } from './deposit-extended.service';
import { DepositUpdateComponent } from '../../entities/deposit/deposit-update.component';
import { AccountService } from '../../core/auth/account.service';
import { Account } from '../../core/user/account.model';
import { JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { DepositService } from '../../entities/deposit/deposit.service';

@Component({
  selector: 'jhi-deposit-update',
  templateUrl: './deposit-extended-update.component.html',
})
export class DepositExtendedUpdateComponent extends DepositUpdateComponent implements OnInit {
  account: Account | null = null;

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected depositService: DepositExtendedService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    private accountService: AccountService
  ) {
    super(dataUtils, eventManager, depositService, activatedRoute, fb);
  }

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(a => (this.account = a));

    this.activatedRoute.data.subscribe(({ deposit }) => {
      if (!deposit.id) {
        const today = moment().startOf('day');
        deposit.postDate = today;
        deposit.createdOn = today;
        deposit.modifiedOn = today;
      }
      deposit.loginId = deposit.loginId ? deposit.loginId : this.account?.login;
      this.updateForm(deposit);
    });
  }

  post(): void {
    this.isSaving = true;
    const deposit = this.createFromForm();
    deposit.isPosted = true;
    if (deposit.id !== undefined) {
      this.subscribeToSaveResponse(this.depositService.update(deposit));
    } else {
      this.subscribeToSaveResponse(this.depositService.create(deposit));
    }
  }
}
