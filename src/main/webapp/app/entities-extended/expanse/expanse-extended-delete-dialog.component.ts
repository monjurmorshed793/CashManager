import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ExpanseExtendedService } from './expanse-extended.service';
import { ExpanseDeleteDialogComponent } from '../../entities/expanse/expanse-delete-dialog.component';
import { ExpanseService } from '../../entities/expanse/expanse.service';

@Component({
  templateUrl: './expanse-extended-delete-dialog.component.html',
})
export class ExpanseExtendedDeleteDialogComponent extends ExpanseDeleteDialogComponent {
  constructor(
    protected expanseService: ExpanseExtendedService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {
    super(expanseService, activeModal, eventManager);
  }

  confirmDelete(id: number): void {
    this.expanseService.delete(id).subscribe(() => {
      this.eventManager.broadcast('expanseListModification');
      this.activeModal.close();
    });
  }
}
