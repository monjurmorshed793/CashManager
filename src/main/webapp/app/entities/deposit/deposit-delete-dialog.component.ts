import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeposit } from 'app/shared/model/deposit.model';
import { DepositService } from './deposit.service';

@Component({
  templateUrl: './deposit-delete-dialog.component.html',
})
export class DepositDeleteDialogComponent {
  deposit?: IDeposit;

  constructor(protected depositService: DepositService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.depositService.delete(id).subscribe(() => {
      this.eventManager.broadcast('depositListModification');
      this.activeModal.close();
    });
  }
}
