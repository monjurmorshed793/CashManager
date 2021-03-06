import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ExpanseDtlComponent } from './expanse-dtl.component';
import { ExpanseDtlDetailComponent } from './expanse-dtl-detail.component';
import { ExpanseDtlUpdateComponent } from './expanse-dtl-update.component';
import { ExpanseDtlDeleteDialogComponent } from './expanse-dtl-delete-dialog.component';
import { expanseDtlRoute } from './expanse-dtl.route';
import { CashManagerSharedModule } from '../../shared/shared.module';

@NgModule({
  imports: [
    CashManagerSharedModule,
    RouterModule.forChild(expanseDtlRoute),
  ] /*,
  declarations: [ExpanseDtlComponent, ExpanseDtlDetailComponent, ExpanseDtlUpdateComponent, ExpanseDtlDeleteDialogComponent],
  entryComponents: [ExpanseDtlDeleteDialogComponent],*/,
})
export class CashManagerExpanseDtlModule {}
