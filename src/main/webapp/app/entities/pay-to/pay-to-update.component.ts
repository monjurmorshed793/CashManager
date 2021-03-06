import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPayTo, PayTo } from 'app/shared/model/pay-to.model';
import { PayToService } from './pay-to.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-pay-to-update',
  templateUrl: './pay-to-update.component.html',
})
export class PayToUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    createdBy: [],
    createdOn: [],
    modifiedBy: [],
    modifiedOn: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected payToService: PayToService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ payTo }) => {
      if (!payTo.id) {
        const today = moment().startOf('day');
        payTo.createdOn = today;
        payTo.modifiedOn = today;
      }

      this.updateForm(payTo);
    });
  }

  updateForm(payTo: IPayTo): void {
    this.editForm.patchValue({
      id: payTo.id,
      name: payTo.name,
      description: payTo.description,
      createdBy: payTo.createdBy,
      createdOn: payTo.createdOn ? payTo.createdOn.format(DATE_TIME_FORMAT) : null,
      modifiedBy: payTo.modifiedBy,
      modifiedOn: payTo.modifiedOn ? payTo.modifiedOn.format(DATE_TIME_FORMAT) : null,
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
    const payTo = this.createFromForm();
    if (payTo.id !== undefined) {
      this.subscribeToSaveResponse(this.payToService.update(payTo));
    } else {
      this.subscribeToSaveResponse(this.payToService.create(payTo));
    }
  }

  private createFromForm(): IPayTo {
    return {
      ...new PayTo(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? moment(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      modifiedOn: this.editForm.get(['modifiedOn'])!.value ? moment(this.editForm.get(['modifiedOn'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPayTo>>): void {
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
