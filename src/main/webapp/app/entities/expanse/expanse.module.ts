import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ExpanseComponent } from './expanse.component';
import { ExpanseDetailComponent } from './expanse-detail.component';
import { ExpanseUpdateComponent } from './expanse-update.component';
import { ExpanseDeleteDialogComponent } from './expanse-delete-dialog.component';
import { expanseRoute } from './expanse.route';
import { CashManagerSharedModule } from '../../shared/shared.module';

@NgModule({
  imports: [
    CashManagerSharedModule,
    RouterModule.forChild(expanseRoute),
  ] /*,
  declarations: [ExpanseComponent, ExpanseDetailComponent, ExpanseUpdateComponent, ExpanseDeleteDialogComponent],
  entryComponents: [ExpanseDeleteDialogComponent],*/,
})
export class CashManagerExpanseModule {}
