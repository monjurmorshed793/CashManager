import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExpanseDtl } from 'app/shared/model/expanse-dtl.model';

@Component({
  selector: 'jhi-expanse-dtl-detail',
  templateUrl: './expanse-dtl-detail.component.html',
})
export class ExpanseDtlDetailComponent implements OnInit {
  expanseDtl: IExpanseDtl | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ expanseDtl }) => (this.expanseDtl = expanseDtl));
  }

  previousState(): void {
    window.history.back();
  }
}
