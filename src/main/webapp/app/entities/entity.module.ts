import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'item',
        loadChildren: () => import('./item/item.module').then(m => m.CashManagerItemModule),
      },
      {
        path: 'pay-to',
        loadChildren: () => import('./pay-to/pay-to.module').then(m => m.CashManagerPayToModule),
      },
      {
        path: 'deposit',
        loadChildren: () => import('./deposit/deposit.module').then(m => m.CashManagerDepositModule),
      },
      {
        path: 'expanse',
        loadChildren: () => import('./expanse/expanse.module').then(m => m.CashManagerExpanseModule),
      },
      {
        path: 'expanse-dtl',
        loadChildren: () => import('./expanse-dtl/expanse-dtl.module').then(m => m.CashManagerExpanseDtlModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class CashManagerEntityModule {}
