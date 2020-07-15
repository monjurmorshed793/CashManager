import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPayTo } from 'app/shared/model/pay-to.model';
import { PayToService } from './pay-to.service';

@Component({
  templateUrl: './pay-to-delete-dialog.component.html',
})
export class PayToDeleteDialogComponent {
  payTo?: IPayTo;

  constructor(protected payToService: PayToService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.payToService.delete(id).subscribe(() => {
      this.eventManager.broadcast('payToListModification');
      this.activeModal.close();
    });
  }
}
