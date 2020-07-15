import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DepositExtendedComponent } from './deposit-extended.component';
import { DepositExtendedDetailComponent } from './deposit-extended-detail.component';
import { DepositExtendedUpdateComponent } from './deposit-extended-update.component';
import { DepositExtendedDeleteDialogComponent } from './deposit-extended-delete-dialog.component';
import { depositExtendedRoute } from './deposit-extended.route';
import { CashManagerSharedModule } from '../../shared/shared.module';
import { DepositComponent } from '../../entities/deposit/deposit.component';
import { DepositDetailComponent } from '../../entities/deposit/deposit-detail.component';
import { DepositUpdateComponent } from '../../entities/deposit/deposit-update.component';
import { DepositDeleteDialogComponent } from '../../entities/deposit/deposit-delete-dialog.component';

@NgModule({
  imports: [CashManagerSharedModule, RouterModule.forChild(depositExtendedRoute)],
  declarations: [
    DepositComponent,
    DepositDetailComponent,
    DepositUpdateComponent,
    DepositDeleteDialogComponent,
    DepositExtendedComponent,
    DepositExtendedDetailComponent,
    DepositExtendedUpdateComponent,
    DepositExtendedDeleteDialogComponent,
  ],
  entryComponents: [DepositExtendedDeleteDialogComponent],
})
export class CashManagerDepositModule {}
