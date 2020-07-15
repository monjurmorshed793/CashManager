import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayToExtendedComponent } from './pay-to-extended.component';
import { PayToExtendedDetailComponent } from './pay-to-extended-detail.component';
import { PayToExtendedUpdateComponent } from './pay-to-extended-update.component';
import { PayToExtendedDeleteDialogComponent } from './pay-to-extended-delete-dialog.component';
import { payToExtendedRoute } from './pay-to-extended.route';
import { CashManagerSharedModule } from '../../shared/shared.module';
import { PayToComponent } from '../../entities/pay-to/pay-to.component';
import { PayToDetailComponent } from '../../entities/pay-to/pay-to-detail.component';
import { PayToUpdateComponent } from '../../entities/pay-to/pay-to-update.component';
import { PayToDeleteDialogComponent } from '../../entities/pay-to/pay-to-delete-dialog.component';

@NgModule({
  imports: [CashManagerSharedModule, RouterModule.forChild(payToExtendedRoute)],
  declarations: [
    PayToComponent,
    PayToDetailComponent,
    PayToUpdateComponent,
    PayToDeleteDialogComponent,
    PayToExtendedComponent,
    PayToExtendedDetailComponent,
    PayToExtendedUpdateComponent,
    PayToExtendedDeleteDialogComponent,
  ],
  entryComponents: [PayToExtendedDeleteDialogComponent],
})
export class CashManagerPayToModule {}
