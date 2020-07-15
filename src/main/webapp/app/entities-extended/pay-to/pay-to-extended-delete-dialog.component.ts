import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PayToExtendedService } from './pay-to-extended.service';
import { PayToDeleteDialogComponent } from '../../entities/pay-to/pay-to-delete-dialog.component';

@Component({
  templateUrl: './pay-to-extended-delete-dialog.component.html',
})
export class PayToExtendedDeleteDialogComponent extends PayToDeleteDialogComponent {
  constructor(protected payToService: PayToExtendedService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {
    super(payToService, activeModal, eventManager);
  }
}
