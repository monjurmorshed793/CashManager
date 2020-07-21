import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { DepositDetailComponent } from '../../entities/deposit/deposit-detail.component';
import { JhiDataUtils } from 'ng-jhipster';

@Component({
  selector: 'jhi-deposit-detail',
  templateUrl: './deposit-extended-detail.component.html',
})
export class DepositExtendedDetailComponent extends DepositDetailComponent implements OnInit {
  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {
    super(dataUtils, activatedRoute);
  }
}
