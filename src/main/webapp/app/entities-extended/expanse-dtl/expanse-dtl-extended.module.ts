import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ExpanseDtlExtendedComponent } from './expanse-dtl-extended.component';
import { ExpanseDtlExtendedDetailComponent } from './expanse-dtl-extended-detail.component';
import { ExpanseDtlExtendedUpdateComponent } from './expanse-dtl-extended-update.component';
import { ExpanseDtlExtendedDeleteDialogComponent } from './expanse-dtl-extended-delete-dialog.component';
import { expanseDtlExtendedRoute } from './expanse-dtl-extended.route';
import { ExpanseDtlComponent } from '../../entities/expanse-dtl/expanse-dtl.component';
import { ExpanseDtlDetailComponent } from '../../entities/expanse-dtl/expanse-dtl-detail.component';
import { ExpanseDtlUpdateComponent } from '../../entities/expanse-dtl/expanse-dtl-update.component';
import { ExpanseDtlDeleteDialogComponent } from '../../entities/expanse-dtl/expanse-dtl-delete-dialog.component';
import { CashManagerSharedModule } from '../../shared/shared.module';

@NgModule({
  imports: [CashManagerSharedModule, RouterModule.forChild(expanseDtlExtendedRoute)],
  declarations: [
    ExpanseDtlComponent,
    ExpanseDtlDetailComponent,
    ExpanseDtlUpdateComponent,
    ExpanseDtlDeleteDialogComponent,
    ExpanseDtlExtendedComponent,
    ExpanseDtlExtendedDetailComponent,
    ExpanseDtlExtendedUpdateComponent,
    ExpanseDtlExtendedDeleteDialogComponent,
  ],
  entryComponents: [ExpanseDtlExtendedDeleteDialogComponent],
})
export class CashManagerExpanseDtlModule {}
