import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPayTo, PayTo } from 'app/shared/model/pay-to.model';
import { PayToService } from './pay-to.service';
import { PayToComponent } from './pay-to.component';
import { PayToDetailComponent } from './pay-to-detail.component';
import { PayToUpdateComponent } from './pay-to-update.component';

@Injectable({ providedIn: 'root' })
export class PayToResolve implements Resolve<IPayTo> {
  constructor(private service: PayToService, private router: Router) {}

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

export const payToRoute: Routes = [
  {
    path: '',
    component: PayToComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'PayTos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PayToDetailComponent,
    resolve: {
      payTo: PayToResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PayTos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PayToUpdateComponent,
    resolve: {
      payTo: PayToResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PayTos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PayToUpdateComponent,
    resolve: {
      payTo: PayToResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PayTos',
    },
    canActivate: [UserRouteAccessService],
  },
];
