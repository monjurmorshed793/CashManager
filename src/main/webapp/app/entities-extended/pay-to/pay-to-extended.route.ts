import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { PayToExtendedService } from './pay-to-extended.service';
import { PayToExtendedComponent } from './pay-to-extended.component';
import { PayToExtendedDetailComponent } from './pay-to-extended-detail.component';
import { PayToExtendedUpdateComponent } from './pay-to-extended-update.component';
import { IPayTo, PayTo } from '../../shared/model/pay-to.model';
import { UserRouteAccessService } from '../../core/auth/user-route-access-service';
import { Authority } from '../../shared/constants/authority.constants';

@Injectable({ providedIn: 'root' })
export class PayToExtendedResolve implements Resolve<IPayTo> {
  constructor(private service: PayToExtendedService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPayTo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((payTo: HttpResponse<PayTo>) => {
          if (payTo.body) {
            return of(payTo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PayTo());
  }
}

export const payToExtendedRoute: Routes = [
  {
    path: '',
    component: PayToExtendedComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'PayTos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PayToExtendedDetailComponent,
    resolve: {
      payTo: PayToExtendedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PayTos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PayToExtendedUpdateComponent,
    resolve: {
      payTo: PayToExtendedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PayTos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PayToExtendedUpdateComponent,
    resolve: {
      payTo: PayToExtendedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PayTos',
    },
    canActivate: [UserRouteAccessService],
  },
];
