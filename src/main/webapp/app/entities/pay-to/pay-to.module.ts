import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CashManagerSharedModule } from 'app/shared/shared.module';
import { PayToComponent } from './pay-to.component';
import { PayToDetailComponent } from './pay-to-detail.component';
import { PayToUpdateComponent } from './pay-to-update.component';
import { PayToDeleteDialogComponent } from './pay-to-delete-dialog.component';
import { payToRoute } from './pay-to.route';

@NgModule({
  imports: [
    CashManagerSharedModule,
    RouterModule.forChild(payToRoute),
  ] /*,
  declarations: [PayToComponent, PayToDetailComponent, PayToUpdateComponent, PayToDeleteDialogComponent],
  entryComponents: [PayToDeleteDialogComponent],*/,
})
export class CashManagerPayToModule {}
