import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeposit } from 'app/shared/model/deposit.model';

@Component({
  selector: 'jhi-deposit-detail',
  templateUrl: './deposit-detail.component.html',
})
export class DepositDetailComponent implements OnInit {
  deposit: IDeposit | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deposit }) => (this.deposit = deposit));
  }

  previousState(): void {
    window.history.back();
  }
}
