import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExpanseDtl } from 'app/shared/model/expanse-dtl.model';
import { ExpanseDtlService } from './expanse-dtl.service';

@Component({
  templateUrl: './expanse-dtl-delete-dialog.component.html',
})
export class ExpanseDtlDeleteDialogComponent {
  expanseDtl?: IExpanseDtl;

  constructor(
    protected expanseDtlService: ExpanseDtlService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.expanseDtlService.delete(id).subscribe(() => {
      this.eventManager.broadcast('expanseDtlListModification');
      this.activeModal.close();
    });
  }
}
