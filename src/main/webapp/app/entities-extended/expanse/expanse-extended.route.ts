import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { ExpanseExtendedService } from './expanse-extended.service';
import { ExpanseExtendedComponent } from './expanse-extended.component';
import { ExpanseExtendedDetailComponent } from './expanse-extended-detail.component';
import { ExpanseExtendedUpdateComponent } from './expanse-extended-update.component';
import { Expanse, IExpanse } from '../../shared/model/expanse.model';
import { Authority } from '../../shared/constants/authority.constants';
import { UserRouteAccessService } from '../../core/auth/user-route-access-service';

@Injectable({ providedIn: 'root' })
export class ExpanseExtendedResolve implements Resolve<IExpanse> {
  constructor(private service: ExpanseExtendedService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExpanse> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((expanse: HttpResponse<Expanse>) => {
          if (expanse.body) {
            return of(expanse.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Expanse());
  }
}

export const expanseExtendedRoute: Routes = [
  {
    path: '',
    component: ExpanseExtendedComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Expanses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ExpanseExtendedDetailComponent,
    resolve: {
      expanse: ExpanseExtendedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Expanses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ExpanseExtendedUpdateComponent,
    resolve: {
      expanse: ExpanseExtendedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Expanses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ExpanseExtendedUpdateComponent,
    resolve: {
      expanse: ExpanseExtendedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Expanses',
    },
    canActivate: [UserRouteAccessService],
  },
];
