import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'item',
        loadChildren: () => import('../entities-extended/item/item-extended.module').then(m => m.CashManagerItemModule),
      },
      {
        path: 'pay-to',
        loadChildren: () => import('../entities-extended/pay-to/pay-to-extended.module').then(m => m.CashManagerPayToModule),
      },
      {
        path: 'deposit',
        loadChildren: () => import('../entities-extended/deposit/deposit-extended.module').then(m => m.CashManagerDepositModule),
      },
      {
        path: 'expanse',
        loadChildren: () => import('../entities-extended/expanse/expanse-extended.module').then(m => m.CashManagerExpanseModule),
      },
      {
        path: 'expanse-dtl',
        loadChildren: () => import('../entities-extended/expanse-dtl/expanse-dtl-extended.module').then(m => m.CashManagerExpanseDtlModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class CashManagerEntityModule {}
