import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { ItemExtendedService } from './item-extended.service';
import { ItemExtendedComponent } from './item-extended.component';
import { ItemExtendedDetailComponent } from './item-extended-detail.component';
import { ItemExtendedUpdateComponent } from './item-extended-update.component';
import { IItem, Item } from '../../shared/model/item.model';
import { UserRouteAccessService } from '../../core/auth/user-route-access-service';
import { Authority } from '../../shared/constants/authority.constants';

@Injectable({ providedIn: 'root' })
export class ItemExtendedResolve implements Resolve<IItem> {
  constructor(private service: ItemExtendedService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((item: HttpResponse<Item>) => {
          if (item.body) {
            return of(item.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Item());
  }
}

export const itemExtendedRoute: Routes = [
  {
    path: '',
    component: ItemExtendedComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Items',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ItemExtendedDetailComponent,
    resolve: {
      item: ItemExtendedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Items',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ItemExtendedUpdateComponent,
    resolve: {
      item: ItemExtendedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Items',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ItemExtendedUpdateComponent,
    resolve: {
      item: ItemExtendedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Items',
    },
    canActivate: [UserRouteAccessService],
  },
];
