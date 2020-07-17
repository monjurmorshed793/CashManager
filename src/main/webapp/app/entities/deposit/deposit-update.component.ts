import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IDeposit, Deposit } from 'app/shared/model/deposit.model';
import { DepositService } from './deposit.service';

@Component({
  selector: 'jhi-deposit-update',
  templateUrl: './deposit-update.component.html',
})
export class DepositUpdateComponent implements OnInit {
  isSaving = false;
  depositDateDp: any;

  editForm = this.fb.group({
    id: [],
    loginId: [null, [Validators.required]],
    depositNo: [null, []],
    depositBy: [null, [Validators.required]],
    depositDate: [null, [Validators.required]],
    medium: [null, [Validators.required]],
    amount: [null, [Validators.required]],
    isPosted: [],
    postDate: [],
    createdBy: [],
    createdOn: [],
    modifiedBy: [],
    modifiedOn: [],
  });

  constructor(protected depositService: DepositService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deposit }) => {
      if (!deposit.id) {
        const today = moment().startOf('day');
        deposit.postDate = today;
        deposit.createdOn = today;
        deposit.modifiedOn = today;
      }

      this.updateForm(deposit);
    });
  }

  updateForm(deposit: IDeposit): void {
    this.editForm.patchValue({
      id: deposit.id,
      loginId: deposit.loginId,
      depositNo: deposit.depositNo,
      depositBy: deposit.depositBy,
      depositDate: deposit.depositDate,
      medium: deposit.medium,
      amount: deposit.amount,
      isPosted: deposit.isPosted,
      postDate: deposit.postDate ? deposit.postDate.format(DATE_TIME_FORMAT) : null,
      createdBy: deposit.createdBy,
      createdOn: deposit.createdOn ? deposit.createdOn.format(DATE_TIME_FORMAT) : null,
      modifiedBy: deposit.modifiedBy,
      modifiedOn: deposit.modifiedOn ? deposit.modifiedOn.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deposit = this.createFromForm();
    if (deposit.id !== undefined) {
      this.subscribeToSaveResponse(this.depositService.update(deposit));
    } else {
      this.subscribeToSaveResponse(this.depositService.create(deposit));
    }
  }

  protected createFromForm(): IDeposit {
    return {
      ...new Deposit(),
      id: this.editForm.get(['id'])!.value,
      loginId: this.editForm.get(['loginId'])!.value,
      depositNo: this.editForm.get(['depositNo'])!.value,
      depositBy: this.editForm.get(['depositBy'])!.value,
      depositDate: this.editForm.get(['depositDate'])!.value,
      medium: this.editForm.get(['medium'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      isPosted: this.editForm.get(['isPosted'])!.value,
      postDate: this.editForm.get(['postDate'])!.value ? moment(this.editForm.get(['postDate'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? moment(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      modifiedOn: this.editForm.get(['modifiedOn'])!.value ? moment(this.editForm.get(['modifiedOn'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeposit>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
