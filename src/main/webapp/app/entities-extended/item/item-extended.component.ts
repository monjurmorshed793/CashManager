import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IItem } from 'app/shared/model/item.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ItemExtendedService } from './item-extended.service';
import { ItemExtendedDeleteDialogComponent } from './item-extended-delete-dialog.component';
import { ItemComponent } from '../../entities/item/item.component';

@Component({
  selector: 'jhi-item',
  templateUrl: './item-extended.component.html',
})
export class ItemExtendedComponent extends ItemComponent implements OnInit, OnDestroy {
  constructor(
    protected itemService: ItemExtendedService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    super(itemService, activatedRoute, dataUtils, router, eventManager, modalService);
  }
}
