import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ItemExtendedService } from './item-extended.service';
import { ItemDeleteDialogComponent } from '../../entities/item/item-delete-dialog.component';
import { IItem } from '../../shared/model/item.model';

@Component({
  templateUrl: './item-extended-delete-dialog.component.html',
})
export class ItemExtendedDeleteDialogComponent extends ItemDeleteDialogComponent {
  item?: IItem;

  constructor(protected itemService: ItemExtendedService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {
    super(itemService, activeModal, eventManager);
  }
}
