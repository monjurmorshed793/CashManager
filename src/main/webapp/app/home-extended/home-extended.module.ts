import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HOME_ROUTE } from './home-extended.route';
import { HomeExtendedComponent } from './home-extended.component';
import { CashManagerSharedModule } from '../shared/shared.module';
import { HomeComponent } from 'app/home/home.component';

@NgModule({
  imports: [CashManagerSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent, HomeExtendedComponent],
})
export class CashManagerHomeExtendedModule {}
