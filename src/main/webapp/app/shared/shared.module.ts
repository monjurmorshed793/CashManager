import { NgModule } from '@angular/core';
import { CashManagerSharedLibsModule } from './shared-libs.module';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { LoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { ExpanseDtlComponent } from 'app/entities/expanse-dtl/expanse-dtl.component';
import { ExpanseDtlDetailComponent } from 'app/entities/expanse-dtl/expanse-dtl-detail.component';
import { ExpanseDtlUpdateComponent } from 'app/entities/expanse-dtl/expanse-dtl-update.component';
import { ExpanseDtlDeleteDialogComponent } from 'app/entities/expanse-dtl/expanse-dtl-delete-dialog.component';
import { ExpanseDtlExtendedComponent } from 'app/entities-extended/expanse-dtl/expanse-dtl-extended.component';
import { ExpanseDtlExtendedDetailComponent } from 'app/entities-extended/expanse-dtl/expanse-dtl-extended-detail.component';
import { ExpanseDtlExtendedUpdateComponent } from 'app/entities-extended/expanse-dtl/expanse-dtl-extended-update.component';
import { ExpanseDtlExtendedDeleteDialogComponent } from 'app/entities-extended/expanse-dtl/expanse-dtl-extended-delete-dialog.component';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [CashManagerSharedLibsModule, RouterModule],
  declarations: [
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    ExpanseDtlComponent,
    ExpanseDtlDetailComponent,
    ExpanseDtlUpdateComponent,
    ExpanseDtlDeleteDialogComponent,
    ExpanseDtlExtendedComponent,
    ExpanseDtlExtendedDetailComponent,
    ExpanseDtlExtendedUpdateComponent,
    ExpanseDtlExtendedDeleteDialogComponent,
  ],
  entryComponents: [LoginModalComponent],
  exports: [
    CashManagerSharedLibsModule,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    ExpanseDtlComponent,
    ExpanseDtlDetailComponent,
    ExpanseDtlUpdateComponent,
    ExpanseDtlDeleteDialogComponent,
    ExpanseDtlExtendedComponent,
    ExpanseDtlExtendedDetailComponent,
    ExpanseDtlExtendedUpdateComponent,
    ExpanseDtlExtendedDeleteDialogComponent,
  ],
})
export class CashManagerSharedModule {}
