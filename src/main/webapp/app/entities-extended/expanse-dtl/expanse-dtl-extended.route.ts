import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { ExpanseDtlExtendedService } from './expanse-dtl-extended.service';
import { ExpanseDtlExtendedComponent } from './expanse-dtl-extended.component';
import { ExpanseDtlExtendedDetailComponent } from './expanse-dtl-extended-detail.component';
import { ExpanseDtlExtendedUpdateComponent } from './expanse-dtl-extended-update.component';
import { ExpanseDtl, IExpanseDtl } from '../../shared/model/expanse-dtl.model';
import { Authority } from '../../shared/constants/authority.constants';
import { UserRouteAccessService } from '../../core/auth/user-route-access-service';

@Injectable({ providedIn: 'root' })
export class ExpanseDtlExtendedResolve implements Resolve<IExpanseDtl> {
  constructor(private service: ExpanseDtlExtendedService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExpanseDtl> | Observable<never> {
    const id = route.params['id'];
    const expanseId = route.params['expanseId'];

    if (id) {
      return this.service.find(id).pipe(
        flatMap((expanseDtl: HttpResponse<ExpanseDtl>) => {
          if (expanseDtl.body) {
            return of(expanseDtl.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    } else if (expanseId) {
      const expanseDtl = new ExpanseDtl();
      expanseDtl.expanseId = expanseId;
      return of(expanseDtl);
    }
    return of(new ExpanseDtl());
  }
}

export const expanseDtlExtendedRoute: Routes = [
  {
    path: '',
    component: ExpanseDtlExtendedComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ExpanseDtls',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ExpanseDtlExtendedDetailComponent,
    resolve: {
      expanseDtl: ExpanseDtlExtendedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ExpanseDtls',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ExpanseDtlExtendedUpdateComponent,
    resolve: {
      expanseDtl: ExpanseDtlExtendedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ExpanseDtls',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':expanseId/new',
    component: ExpanseDtlExtendedUpdateComponent,
    resolve: {
      expanseDtl: ExpanseDtlExtendedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ExpanseDtls',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ExpanseDtlExtendedUpdateComponent,
    resolve: {
      expanseDtl: ExpanseDtlExtendedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ExpanseDtls',
    },
    canActivate: [UserRouteAccessService],
  },
];
