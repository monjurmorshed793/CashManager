import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ExpanseDtlDetailComponent } from '../../entities/expanse-dtl/expanse-dtl-detail.component';

@Component({
  selector: 'jhi-expanse-dtl-detail',
  templateUrl: './expanse-dtl-extended-detail.component.html',
})
export class ExpanseDtlExtendedDetailComponent extends ExpanseDtlDetailComponent implements OnInit {
  constructor(protected activatedRoute: ActivatedRoute) {
    super(activatedRoute);
  }
}
