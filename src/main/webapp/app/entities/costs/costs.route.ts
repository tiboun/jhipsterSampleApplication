import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CostsComponent } from './costs.component';
import { CostsDetailComponent } from './costs-detail.component';
import { CostsPopupComponent } from './costs-dialog.component';
import { CostsDeletePopupComponent } from './costs-delete-dialog.component';

export const costsRoute: Routes = [
    {
        path: 'costs',
        component: CostsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Costs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'costs/:id',
        component: CostsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Costs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const costsPopupRoute: Routes = [
    {
        path: 'costs-new',
        component: CostsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Costs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'costs/:id/edit',
        component: CostsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Costs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'costs/:id/delete',
        component: CostsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Costs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
