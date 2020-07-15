import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ItemDetailComponent } from '../../entities/item/item-detail.component';

@Component({
  selector: 'jhi-item-detail',
  templateUrl: './item-extended-detail.component.html',
})
export class ItemExtendedDetailComponent extends ItemDetailComponent implements OnInit {
  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {
    super(dataUtils, activatedRoute);
  }
}
