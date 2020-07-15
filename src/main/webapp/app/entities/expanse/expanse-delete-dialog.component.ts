import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExpanse } from 'app/shared/model/expanse.model';
import { ExpanseService } from './expanse.service';

@Component({
  templateUrl: './expanse-delete-dialog.component.html',
})
export class ExpanseDeleteDialogComponent {
  expanse?: IExpanse;

  constructor(protected expanseService: ExpanseService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.expanseService.delete(id).subscribe(() => {
      this.eventManager.broadcast('expanseListModification');
      this.activeModal.close();
    });
  }
}
