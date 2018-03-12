import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TimesheetsComponent } from './timesheets.component';
import { TimesheetsDetailComponent } from './timesheets-detail.component';
import { TimesheetsPopupComponent } from './timesheets-dialog.component';
import { TimesheetsDeletePopupComponent } from './timesheets-delete-dialog.component';

@Injectable()
export class TimesheetsResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const timesheetsRoute: Routes = [
    {
        path: 'timesheets',
        component: TimesheetsComponent,
        resolve: {
            'pagingParams': TimesheetsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Timesheets'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'timesheets/:id',
        component: TimesheetsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Timesheets'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const timesheetsPopupRoute: Routes = [
    {
        path: 'timesheets-new',
        component: TimesheetsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Timesheets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'timesheets/:id/edit',
        component: TimesheetsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Timesheets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'timesheets/:id/delete',
        component: TimesheetsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Timesheets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
