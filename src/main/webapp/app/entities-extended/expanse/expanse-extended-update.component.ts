import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ExpanseExtendedService } from './expanse-extended.service';
import { ExpanseUpdateComponent } from '../../entities/expanse/expanse-update.component';
import { PayToExtendedService } from '../pay-to/pay-to-extended.service';
import { IPayTo } from '../../shared/model/pay-to.model';
import { AccountService } from '../../core/auth/account.service';
import { Account } from '../../core/user/account.model';
import { IExpanse } from '../../shared/model/expanse.model';
import { ExpanseDtl, IExpanseDtl } from '../../shared/model/expanse-dtl.model';
import { ExpanseDtlService } from '../../entities/expanse-dtl/expanse-dtl.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DATE_TIME_FORMAT } from '../../shared/constants/input.constants';
import { ItemService } from '../../entities/item/item.service';
import { IItem, Item } from '../../shared/model/item.model';

@Component({
  selector: 'jhi-expanse-update',
  templateUrl: './expanse-extended-update.component.html',
})
export class ExpanseExtendedUpdateComponent extends ExpanseUpdateComponent implements OnInit {
  account: Account | null = null;
  expanseDtls: IExpanseDtl[] = [];
  expanseDtl: IExpanseDtl | null = null;
  isAddingNew: Boolean = false;
  expanse: IExpanse | null = null;
  items: Item[] = [];

  expanseDtlForm = this.fb.group({
    id: [],
    quantity: [null, [Validators.required]],
    unitPrice: [null, [Validators.required]],
    amount: [null, [Validators.required]],
    createdBy: [],
    createdOn: [],
    modifiedBy: [],
    modifiedOn: [],
    expanseId: [],
    itemId: [null, Validators.required],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected expanseService: ExpanseExtendedService,
    protected payToService: PayToExtendedService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    private accountService: AccountService,
    private expanseDtlService: ExpanseDtlService,
    private modalService: NgbModal,
    private itemService: ItemService,
    private router: Router
  ) {
    super(dataUtils, eventManager, expanseService, payToService, activatedRoute, fb);
  }

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(a => (this.account = a));
    this.expanseService.setNewId(null);
    this.activatedRoute.data.subscribe(({ expanse }) => {
      if (!expanse.id) {
        this.expanseService.setExpanseId(expanse.id);
        const today = moment().startOf('day');
        expanse.postDate = today;
        expanse.createdOn = today;
        expanse.modifiedOn = today;
        this.expanseDtls = [];
      } else {
        this.expanseDtlService
          .query({
            'expanseId.equals': expanse.id,
          })
          .subscribe(res => {
            this.expanseDtls = res.body ? res.body : [];
          });
      }

      if (!expanse.loginId) expanse.loginId = this.account?.login;
      if (!expanse.voucherDate) expanse.voucherDate = moment();
      this.expanse = expanse;
      this.updateForm(expanse);

      this.payToService.query({ size: 10000 }).subscribe((res: HttpResponse<IPayTo[]>) => (this.paytos = res.body || []));
      this.itemService.query({ size: 10000 }).subscribe((res: HttpResponse<IItem[]>) => (this.items = res.body || []));
    });
  }

  updateExpanseDtlForm(expanseDtl: IExpanseDtl): void {
    this.editForm.patchValue({
      id: expanseDtl.id,
      quantity: expanseDtl.quantity,
      unitPrice: expanseDtl.unitPrice,
      amount: expanseDtl.amount,
      createdBy: expanseDtl.createdBy,
      createdOn: expanseDtl.createdOn ? expanseDtl.createdOn.format(DATE_TIME_FORMAT) : null,
      modifiedBy: expanseDtl.modifiedBy,
      modifiedOn: expanseDtl.modifiedOn ? expanseDtl.modifiedOn.format(DATE_TIME_FORMAT) : null,
      expanseId: expanseDtl.expanseId,
      itemId: expanseDtl.itemId,
    });
  }

  private createExpanseDtlFromForm(): IExpanseDtl {
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
      expanseId: this.editForm.get(['expanseId'])!.value,
      itemId: this.editForm.get(['itemId'])!.value,
    };
  }

  addItem(content: any, expanseDtl?: IExpanseDtl): void {
    if (expanseDtl) this.isAddingNew = false;
    else this.isAddingNew = true;
    this.updateExpanseDtlForm(expanseDtl ? expanseDtl : new ExpanseDtl());
    this.modalService.open(content, { size: 'lg' });
  }

  addItemToList(): void {
    this.modalService.dismissAll();
    this.expanseDtl = this.createExpanseDtlFromForm();

    if (this.isAddingNew) this.expanseDtls.push(this.expanseDtl);
  }

  cancel(): void {
    this.modalService.dismissAll();
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExpanse>>): void {
    result.subscribe(
      res => {
        this.expanse = res.body;
        if (this.isAddingNew) {
          this.expanseService.setNewId(res?.body?.id!);
        }
        this.onSaveSuccess();
      },
      () => this.onSaveError()
    );
  }

  save(): void {
    this.isSaving = true;
    const expanse = this.createFromForm();
    if (expanse.id !== undefined) {
      this.subscribeToSaveResponse(this.expanseService.update(expanse));
    } else {
      this.isAddingNew = true;
      this.subscribeToSaveResponse(this.expanseService.create(expanse));
    }
  }

  post(): void {
    this.isSaving = true;
    const expanse = this.createFromForm();
    expanse.isPosted = true;
    expanse.postDate = moment();
    if (expanse.id !== undefined) {
      this.subscribeToSaveResponse(this.expanseService.update(expanse));
    } else {
      this.isAddingNew = true;
      this.subscribeToSaveResponse(this.expanseService.create(expanse));
    }
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    //this.previousState();
  }

  previousState(): void {
    if (this.expanse?.isPosted) {
      window.history.back();
    } else {
      this.router.navigate(['/expanse']);
    }
  }
}
