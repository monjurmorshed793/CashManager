import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DepositComponent } from './deposit.component';
import { DepositDetailComponent } from './deposit-detail.component';
import { DepositUpdateComponent } from './deposit-update.component';
import { DepositDeleteDialogComponent } from './deposit-delete-dialog.component';
import { depositRoute } from './deposit.route';
import { CashManagerSharedModule } from '../../shared/shared.module';

@NgModule({
  imports: [
    CashManagerSharedModule,
    RouterModule.forChild(depositRoute),
  ] /*,
  declarations: [DepositComponent, DepositDetailComponent, DepositUpdateComponent, DepositDeleteDialogComponent],
  entryComponents: [DepositDeleteDialogComponent],*/,
})
export class CashManagerDepositModule {}
