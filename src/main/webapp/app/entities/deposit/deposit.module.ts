import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CashManagerSharedModule } from 'app/shared/shared.module';
import { DepositComponent } from './deposit.component';
import { DepositDetailComponent } from './deposit-detail.component';
import { DepositUpdateComponent } from './deposit-update.component';
import { DepositDeleteDialogComponent } from './deposit-delete-dialog.component';
import { depositRoute } from './deposit.route';

@NgModule({
  imports: [CashManagerSharedModule, RouterModule.forChild(depositRoute)],
  declarations: [DepositComponent, DepositDetailComponent, DepositUpdateComponent, DepositDeleteDialogComponent],
  entryComponents: [DepositDeleteDialogComponent],
})
export class CashManagerDepositModule {}
