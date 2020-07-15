import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IExpanse, Expanse } from 'app/shared/model/expanse.model';
import { ExpanseService } from './expanse.service';
import { ExpanseComponent } from './expanse.component';
import { ExpanseDetailComponent } from './expanse-detail.component';
import { ExpanseUpdateComponent } from './expanse-update.component';

@Injectable({ providedIn: 'root' })
export class ExpanseResolve implements Resolve<IExpanse> {
  constructor(private service: ExpanseService, private router: Router) {}

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

export const expanseRoute: Routes = [
  {
    path: '',
    component: ExpanseComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Expanses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ExpanseDetailComponent,
    resolve: {
      expanse: ExpanseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Expanses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ExpanseUpdateComponent,
    resolve: {
      expanse: ExpanseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Expanses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ExpanseUpdateComponent,
    resolve: {
      expanse: ExpanseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Expanses',
    },
    canActivate: [UserRouteAccessService],
  },
];
