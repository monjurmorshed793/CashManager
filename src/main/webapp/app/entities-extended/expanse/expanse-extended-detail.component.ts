import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ExpanseDetailComponent } from '../../entities/expanse/expanse-detail.component';
import { IExpanse } from '../../shared/model/expanse.model';

@Component({
  selector: 'jhi-expanse-detail',
  templateUrl: './expanse-extended-detail.component.html',
})
export class ExpanseExtendedDetailComponent extends ExpanseDetailComponent implements OnInit {
  expanse: IExpanse | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {
    super(dataUtils, activatedRoute);
  }
}
