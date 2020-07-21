import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { merge, Observable, Subject } from 'rxjs';
import * as moment from 'moment';

import { ExpanseDtlUpdateComponent } from '../../entities/expanse-dtl/expanse-dtl-update.component';
import { ExpanseDtlExtendedService } from './expanse-dtl-extended.service';
import { ExpanseService } from '../../entities/expanse/expanse.service';
import { IItem } from '../../shared/model/item.model';
import { IExpanse } from '../../shared/model/expanse.model';
import { ItemExtendedService } from '../item/item-extended.service';
import { NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';
import { debounceTime, distinctUntilChanged, filter, map } from 'rxjs/operators';
import { ExpanseDtl, IExpanseDtl } from 'app/shared/model/expanse-dtl.model';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ExpanseExtendedService } from 'app/entities-extended/expanse/expanse-extended.service';

type SelectableEntity = IExpanse | IItem;

@Component({
  selector: 'jhi-expanse-dtl-update',
  templateUrl: './expanse-dtl-extended-update.component.html',
})
export class ExpanseDtlExtendedUpdateComponent extends ExpanseDtlUpdateComponent implements OnInit {
  itemNames: string[] = [];
  itemNameMapItem = new Map();
  selectedItemName: any = {};
  itemIdMapItem = new Map();

  @ViewChild('instance', { static: true }) instance: NgbTypeahead | undefined;
  focus$ = new Subject<string>();
  click$ = new Subject<string>();

  editForm = this.fb.group({
    id: [],
    quantity: [],
    unitPrice: [],
    amount: [],
    createdBy: [],
    createdOn: [],
    modifiedBy: [],
    modifiedOn: [],
    expanseId: [],
    itemId: [],
    itemName: [],
  });

  constructor(
    protected expanseDtlService: ExpanseDtlExtendedService,
    protected itemService: ItemExtendedService,
    protected expanseService: ExpanseExtendedService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {
    super(expanseDtlService, itemService, expanseService, activatedRoute, fb);
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ expanseDtl }) => {
      if (!expanseDtl.id) {
        const today = moment().startOf('day');
        expanseDtl.createdOn = today;
        expanseDtl.modifiedOn = today;
      }

      if (expanseDtl.itemId) {
        this.selectedItemName = this.itemIdMapItem.get(expanseDtl.itemId);
      }
      this.itemService.query({ size: 10000 }).subscribe((res: HttpResponse<IItem[]>) => {
        this.items = res.body || [];
        this.itemNameMapItem = new Map();
        this.itemIdMapItem = new Map();
        this.items.forEach(i => {
          this.itemNames.push(i.name!);
          this.itemNameMapItem.set(i.name!, i);
          this.itemIdMapItem.set(i.id!, i);
        });
        this.updateForm(expanseDtl);
      });
      // this.expanseService.query().subscribe((res: HttpResponse<IExpanse[]>) => (this.expanses = res.body || []));
    });
  }

  itemSelected(selectedItem: string): void {
    this.selectedItemName = selectedItem;
  }

  save(): void {
    this.isSaving = true;
    const expanseDtl = this.createFromForm();
    // expanseDtl.itemId = this.itemNameMapItem[this.selectedItemName].id;
    if (expanseDtl.id !== undefined) {
      this.subscribeToSaveResponse(this.expanseDtlService.update(expanseDtl));
    } else {
      this.subscribeToSaveResponse(this.expanseDtlService.create(expanseDtl));
    }
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
      itemName: expanseDtl.itemId ? this.itemIdMapItem.get(expanseDtl.itemId).name : null,
    });
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExpanseDtl>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
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
      itemId: this.editForm.get(['itemName'])!.value ? this.itemNameMapItem.get(this.editForm.get(['itemName'])!.value).id! : undefined,
      expanseId: this.editForm.get(['expanseId'])!.value,
    };
  }

  search = (text$: Observable<string>) => {
    const debouncedText$ = text$.pipe(debounceTime(200), distinctUntilChanged());
    const clicksWithClosedPopup$ = this.click$.pipe(filter(() => !this.instance?.isPopupOpen()));
    const inputFocus$ = this.focus$;

    return merge(debouncedText$, inputFocus$, clicksWithClosedPopup$).pipe(
      map(term => (term === '' ? this.itemNames : this.itemNames.filter(v => v.toLowerCase().includes(term.toLowerCase()))).slice(0, 10))
    );
  };
}
