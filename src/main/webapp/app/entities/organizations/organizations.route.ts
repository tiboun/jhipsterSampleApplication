import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { OrganizationsComponent } from './organizations.component';
import { OrganizationsDetailComponent } from './organizations-detail.component';
import { OrganizationsPopupComponent } from './organizations-dialog.component';
import { OrganizationsDeletePopupComponent } from './organizations-delete-dialog.component';

export const organizationsRoute: Routes = [
    {
        path: 'organizations',
        component: OrganizationsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Organizations'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'organizations/:id',
        component: OrganizationsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Organizations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const organizationsPopupRoute: Routes = [
    {
        path: 'organizations-new',
        component: OrganizationsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Organizations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'organizations/:id/edit',
        component: OrganizationsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Organizations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'organizations/:id/delete',
        component: OrganizationsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Organizations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
