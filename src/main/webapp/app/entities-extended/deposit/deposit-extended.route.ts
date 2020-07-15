import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { DepositExtendedService } from './deposit-extended.service';
import { DepositExtendedComponent } from './deposit-extended.component';
import { DepositExtendedDetailComponent } from './deposit-extended-detail.component';
import { DepositExtendedUpdateComponent } from './deposit-extended-update.component';
import { Deposit, IDeposit } from '../../shared/model/deposit.model';

@Injectable({ providedIn: 'root' })
export class DepositExtendedResolve implements Resolve<IDeposit> {
  constructor(private service: DepositExtendedService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDeposit> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((deposit: HttpResponse<Deposit>) => {
          if (deposit.body) {
            return of(deposit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Deposit());
  }
}

export const depositExtendedRoute: Routes = [
  {
    path: '',
    component: DepositExtendedComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Deposits',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DepositExtendedDetailComponent,
    resolve: {
      deposit: DepositExtendedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Deposits',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DepositExtendedUpdateComponent,
    resolve: {
      deposit: DepositExtendedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Deposits',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DepositExtendedUpdateComponent,
    resolve: {
      deposit: DepositExtendedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Deposits',
    },
    canActivate: [UserRouteAccessService],
  },
];
