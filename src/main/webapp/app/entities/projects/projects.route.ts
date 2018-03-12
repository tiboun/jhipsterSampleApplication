import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ProjectsComponent } from './projects.component';
import { ProjectsDetailComponent } from './projects-detail.component';
import { ProjectsPopupComponent } from './projects-dialog.component';
import { ProjectsDeletePopupComponent } from './projects-delete-dialog.component';

export const projectsRoute: Routes = [
    {
        path: 'projects',
        component: ProjectsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Projects'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'projects/:id',
        component: ProjectsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Projects'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const projectsPopupRoute: Routes = [
    {
        path: 'projects-new',
        component: ProjectsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Projects'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'projects/:id/edit',
        component: ProjectsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Projects'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'projects/:id/delete',
        component: ProjectsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Projects'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
