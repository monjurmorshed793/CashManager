import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IExpanseDtl, ExpanseDtl } from 'app/shared/model/expanse-dtl.model';
import { ExpanseDtlService } from './expanse-dtl.service';
import { IItem } from 'app/shared/model/item.model';
import { ItemService } from 'app/entities/item/item.service';
import { IExpanse } from 'app/shared/model/expanse.model';
import { ExpanseService } from 'app/entities/expanse/expanse.service';

type SelectableEntity = IItem | IExpanse;

@Component({
  selector: 'jhi-expanse-dtl-update',
  templateUrl: './expanse-dtl-update.component.html',
})
export class ExpanseDtlUpdateComponent implements OnInit {
  isSaving = false;
  items: IItem[] = [];
  expanses: IExpanse[] = [];

  editForm = this.fb.group({
    id: [],
    quantity: [],
    unitPrice: [],
    amount: [],
    createdBy: [],
    createdOn: [],
    modifiedBy: [],
    modifiedOn: [],
    itemId: [null, Validators.required],
    expanseId: [],
  });

  constructor(
    protected expanseDtlService: ExpanseDtlService,
    protected itemService: ItemService,
    protected expanseService: ExpanseService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ expanseDtl }) => {
      if (!expanseDtl.id) {
        const today = moment().startOf('day');
        expanseDtl.createdOn = today;
        expanseDtl.modifiedOn = today;
      }

      this.updateForm(expanseDtl);

      this.itemService.query().subscribe((res: HttpResponse<IItem[]>) => (this.items = res.body || []));

      this.expanseService.query().subscribe((res: HttpResponse<IExpanse[]>) => (this.expanses = res.body || []));
    });
  }

  updateForm(expanseDtl: IExpanseDtl): void {
    this.editForm.patchValue({
      id: expanseDtl.id,
      quantity: expanseDtl.quantity,
      unitPrice: expanseDtl.unitPrice,
      amount: expanseDtl.amount,
      createdBy: expanseDtl.createdBy,
      createdOn: expanseDtl.createdOn ? expanseDtl.createdOn.format(DATE_TIME_FORMAT) : null,
      modifiedBy: expanseDtl.modifiedBy,
      modifiedOn: expanseDtl.modifiedOn ? expanseDtl.modifiedOn.format(DATE_TIME_FORMAT) : null,
      itemId: expanseDtl.itemId,
      expanseId: expanseDtl.expanseId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const expanseDtl = this.createFromForm();
    if (expanseDtl.id !== undefined) {
      this.subscribeToSaveResponse(this.expanseDtlService.update(expanseDtl));
    } else {
      this.subscribeToSaveResponse(this.expanseDtlService.create(expanseDtl));
    }
  }

  protected createFromForm(): IExpanseDtl {
    return {
      ...new ExpanseDtl(),
      id: this.editForm.get(['id'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      unitPrice: this.editForm.get(['unitPrice'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? moment(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      modifiedOn: this.editForm.get(['modifiedOn'])!.value ? moment(this.editForm.get(['modifiedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      itemId: this.editForm.get(['itemId'])!.value,
      expanseId: this.editForm.get(['expanseId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExpanseDtl>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
