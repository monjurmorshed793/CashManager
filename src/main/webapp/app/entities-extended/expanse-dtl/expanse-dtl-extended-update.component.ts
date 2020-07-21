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

type SelectableEntity = IExpanse | IItem;

@Component({
  selector: 'jhi-expanse-dtl-update',
  templateUrl: './expanse-dtl-extended-update.component.html',
})
export class ExpanseDtlExtendedUpdateComponent extends ExpanseDtlUpdateComponent implements OnInit {
  itemNames: string[] = [];
  itemNameMapItem = new Map();
  selectedItemName: string | null = null;

  @ViewChild('instance', { static: true }) instance: NgbTypeahead | undefined;
  focus$ = new Subject<string>();
  click$ = new Subject<string>();

  editForm = this.fb.group({
    id: [],
    quantity: [null],
    unitPrice: [null],
    amount: [null],
    createdBy: [],
    createdOn: [],
    modifiedBy: [],
    modifiedOn: [],
    expanseId: [],
    itemId: [null, Validators.required],
  });

  constructor(
    protected expanseDtlService: ExpanseDtlExtendedService,
    protected itemService: ItemExtendedService,
    protected expanseService: ExpanseService,
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

      this.updateForm(expanseDtl);

      this.itemService.query({ size: 10000 }).subscribe((res: HttpResponse<IItem[]>) => {
        this.items = res.body || [];
        this.itemNameMapItem = new Map();
        this.items.forEach(i => {
          this.itemNames.push(i.name!);
          this.itemNameMapItem.set(i.name!, i);
        });
      });

      this.expanseService.query().subscribe((res: HttpResponse<IExpanse[]>) => (this.expanses = res.body || []));
    });
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
