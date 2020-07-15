import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DepositExtendedService } from './deposit-extended.service';
import { DepositDeleteDialogComponent } from '../../entities/deposit/deposit-delete-dialog.component';

@Component({
  templateUrl: './deposit-extended-delete-dialog.component.html',
})
export class DepositExtendedDeleteDialogComponent extends DepositDeleteDialogComponent {
  constructor(
    protected depositService: DepositExtendedService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {
    super(depositService, activeModal, eventManager);
  }
}
