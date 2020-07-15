import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IExpanse } from 'app/shared/model/expanse.model';

@Component({
  selector: 'jhi-expanse-detail',
  templateUrl: './expanse-detail.component.html',
})
export class ExpanseDetailComponent implements OnInit {
  expanse: IExpanse | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ expanse }) => (this.expanse = expanse));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
