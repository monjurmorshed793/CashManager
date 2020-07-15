import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExpanseDtl } from 'app/shared/model/expanse-dtl.model';
import { ExpanseDtlExtendedService } from './expanse-dtl-extended.service';

@Component({
  templateUrl: './expanse-dtl-extended-delete-dialog.component.html',
})
export class ExpanseDtlExtendedDeleteDialogComponent {
  expanseDtl?: IExpanseDtl;

  constructor(
    protected expanseDtlService: ExpanseDtlExtendedService,
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
