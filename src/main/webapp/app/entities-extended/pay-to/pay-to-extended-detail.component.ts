import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { PayToDetailComponent } from '../../entities/pay-to/pay-to-detail.component';

@Component({
  selector: 'jhi-pay-to-detail',
  templateUrl: './pay-to-extended-detail.component.html',
})
export class PayToExtendedDetailComponent extends PayToDetailComponent implements OnInit {
  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {
    super(dataUtils, activatedRoute);
  }
}
