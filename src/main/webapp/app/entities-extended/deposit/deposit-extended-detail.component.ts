import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { DepositDetailComponent } from '../../entities/deposit/deposit-detail.component';

@Component({
  selector: 'jhi-deposit-detail',
  templateUrl: './deposit-extended-detail.component.html',
})
export class DepositExtendedDetailComponent extends DepositDetailComponent implements OnInit {
  constructor(protected activatedRoute: ActivatedRoute) {
    super(activatedRoute);
  }
}
