import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CashManagerSharedModule } from 'app/shared/shared.module';
import { ExpanseExtendedComponent } from './expanse-extended.component';
import { ExpanseExtendedDetailComponent } from './expanse-extended-detail.component';
import { ExpanseExtendedUpdateComponent } from './expanse-extended-update.component';
import { ExpanseExtendedDeleteDialogComponent } from './expanse-extended-delete-dialog.component';
import { expanseExtendedRoute } from './expanse-extended.route';

@NgModule({
  imports: [CashManagerSharedModule, RouterModule.forChild(expanseExtendedRoute)],
  declarations: [
    ExpanseExtendedComponent,
    ExpanseExtendedDetailComponent,
    ExpanseExtendedUpdateComponent,
    ExpanseExtendedDeleteDialogComponent,
  ],
  entryComponents: [ExpanseExtendedDeleteDialogComponent],
})
export class CashManagerExpanseModule {}
