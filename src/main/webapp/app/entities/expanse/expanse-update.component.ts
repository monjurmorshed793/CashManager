import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IExpanse, Expanse } from 'app/shared/model/expanse.model';
import { ExpanseService } from './expanse.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IPayTo } from 'app/shared/model/pay-to.model';
import { PayToService } from 'app/entities/pay-to/pay-to.service';

@Component({
  selector: 'jhi-expanse-update',
  templateUrl: './expanse-update.component.html',
})
export class ExpanseUpdateComponent implements OnInit {
  isSaving = false;
  paytos: IPayTo[] = [];
  voucherDateDp: any;

  editForm = this.fb.group({
    id: [],
    loginId: [null, [Validators.required]],
    voucherNo: [null, []],
    voucherDate: [null, [Validators.required]],
    month: [null, [Validators.required]],
    notes: [null, [Validators.required]],
    isPosted: [],
    postDate: [],
    createdBy: [],
    createdOn: [],
    modifiedBy: [],
    modifiedOn: [],
    payToId: [null, Validators.required],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected expanseService: ExpanseService,
    protected payToService: PayToService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ expanse }) => {
      if (!expanse.id) {
        const today = moment().startOf('day');
        expanse.postDate = today;
        expanse.createdOn = today;
        expanse.modifiedOn = today;
      }

      this.updateForm(expanse);

      this.payToService.query().subscribe((res: HttpResponse<IPayTo[]>) => (this.paytos = res.body || []));
    });
  }

  updateForm(expanse: IExpanse): void {
    this.editForm.patchValue({
      id: expanse.id,
      loginId: expanse.loginId,
      voucherNo: expanse.voucherNo,
      voucherDate: expanse.voucherDate,
      month: expanse.month,
      notes: expanse.notes,
      isPosted: expanse.isPosted,
      postDate: expanse.postDate ? expanse.postDate.format(DATE_TIME_FORMAT) : null,
      createdBy: expanse.createdBy,
      createdOn: expanse.createdOn ? expanse.createdOn.format(DATE_TIME_FORMAT) : null,
      modifiedBy: expanse.modifiedBy,
      modifiedOn: expanse.modifiedOn ? expanse.modifiedOn.format(DATE_TIME_FORMAT) : null,
      payToId: expanse.payToId,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('cashManagerApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const expanse = this.createFromForm();
    if (expanse.id !== undefined) {
      this.subscribeToSaveResponse(this.expanseService.update(expanse));
    } else {
      this.subscribeToSaveResponse(this.expanseService.create(expanse));
    }
  }

  protected createFromForm(): IExpanse {
    return {
      ...new Expanse(),
      id: this.editForm.get(['id'])!.value,
      loginId: this.editForm.get(['loginId'])!.value,
      voucherNo: this.editForm.get(['voucherNo'])!.value,
      voucherDate: this.editForm.get(['voucherDate'])!.value,
      month: this.editForm.get(['month'])!.value,
      notes: this.editForm.get(['notes'])!.value,
      isPosted: this.editForm.get(['isPosted'])!.value,
      postDate: this.editForm.get(['postDate'])!.value ? moment(this.editForm.get(['postDate'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? moment(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      modifiedOn: this.editForm.get(['modifiedOn'])!.value ? moment(this.editForm.get(['modifiedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      payToId: this.editForm.get(['payToId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExpanse>>): void {
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

  trackById(index: number, item: IPayTo): any {
    return item.id;
  }
}
