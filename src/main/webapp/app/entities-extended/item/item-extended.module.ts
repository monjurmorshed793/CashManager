import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItemExtendedComponent } from './item-extended.component';
import { ItemExtendedDetailComponent } from './item-extended-detail.component';
import { ItemExtendedUpdateComponent } from './item-extended-update.component';
import { ItemExtendedDeleteDialogComponent } from './item-extended-delete-dialog.component';
import { itemExtendedRoute } from './item-extended.route';
import { CashManagerSharedModule } from '../../shared/shared.module';
import { ItemComponent } from '../../entities/item/item.component';
import { ItemDetailComponent } from '../../entities/item/item-detail.component';
import { ItemUpdateComponent } from '../../entities/item/item-update.component';
import { ItemDeleteDialogComponent } from '../../entities/item/item-delete-dialog.component';

@NgModule({
  imports: [CashManagerSharedModule, RouterModule.forChild(itemExtendedRoute)],
  declarations: [
    ItemComponent,
    ItemDetailComponent,
    ItemUpdateComponent,
    ItemDeleteDialogComponent,
    ItemExtendedComponent,
    ItemExtendedDetailComponent,
    ItemExtendedUpdateComponent,
    ItemExtendedDeleteDialogComponent,
  ],
  entryComponents: [ItemExtendedDeleteDialogComponent],
})
export class CashManagerItemModule {}
