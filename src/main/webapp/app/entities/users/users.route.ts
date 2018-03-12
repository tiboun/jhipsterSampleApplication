import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { UsersComponent } from './users.component';
import { UsersDetailComponent } from './users-detail.component';
import { UsersPopupComponent } from './users-dialog.component';
import { UsersDeletePopupComponent } from './users-delete-dialog.component';

export const usersRoute: Routes = [
    {
        path: 'users',
        component: UsersComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Users'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'users/:id',
        component: UsersDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Users'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const usersPopupRoute: Routes = [
    {
        path: 'users-new',
        component: UsersPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Users'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'users/:id/edit',
        component: UsersPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Users'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'users/:id/delete',
        component: UsersDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Users'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
