import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CashManagerSharedModule } from 'app/shared/shared.module';
import { ExpanseExtendedComponent } from './expanse-extended.component';
import { ExpanseExtendedDetailComponent } from './expanse-extended-detail.component';
import { ExpanseExtendedUpdateComponent } from './expanse-extended-update.component';
import { ExpanseExtendedDeleteDialogComponent } from './expanse-extended-delete-dialog.component';
import { expanseExtendedRoute } from './expanse-extended.route';
import { ExpanseComponent } from '../../entities/expanse/expanse.component';
import { ExpanseDetailComponent } from '../../entities/expanse/expanse-detail.component';
import { ExpanseUpdateComponent } from '../../entities/expanse/expanse-update.component';
import { ExpanseDeleteDialogComponent } from '../../entities/expanse/expanse-delete-dialog.component';
import { ExpanseDtlComponent } from '../../entities/expanse-dtl/expanse-dtl.component';
import { ExpanseDtlDetailComponent } from '../../entities/expanse-dtl/expanse-dtl-detail.component';
import { ExpanseDtlUpdateComponent } from '../../entities/expanse-dtl/expanse-dtl-update.component';
import { ExpanseDtlDeleteDialogComponent } from '../../entities/expanse-dtl/expanse-dtl-delete-dialog.component';
import { ExpanseDtlExtendedComponent } from '../expanse-dtl/expanse-dtl-extended.component';
import { ExpanseDtlExtendedDetailComponent } from '../expanse-dtl/expanse-dtl-extended-detail.component';
import { ExpanseDtlExtendedUpdateComponent } from '../expanse-dtl/expanse-dtl-extended-update.component';
import { ExpanseDtlExtendedDeleteDialogComponent } from '../expanse-dtl/expanse-dtl-extended-delete-dialog.component';
import { ExpanseDtlDirectiveComponent } from './expanse-dtl-directive.component';

@NgModule({
  imports: [CashManagerSharedModule, RouterModule.forChild(expanseExtendedRoute)],
  declarations: [
    ExpanseComponent,
    ExpanseDetailComponent,
    ExpanseUpdateComponent,
    ExpanseDeleteDialogComponent,
    ExpanseExtendedComponent,
    ExpanseExtendedDetailComponent,
    ExpanseExtendedUpdateComponent,
    ExpanseExtendedDeleteDialogComponent,
    ExpanseDtlDirectiveComponent,
  ],
  entryComponents: [ExpanseExtendedDeleteDialogComponent],
})
export class CashManagerExpanseModule {}
