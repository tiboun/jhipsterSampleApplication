import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    ProjectsService,
    ProjectsPopupService,
    ProjectsComponent,
    ProjectsDetailComponent,
    ProjectsDialogComponent,
    ProjectsPopupComponent,
    ProjectsDeletePopupComponent,
    ProjectsDeleteDialogComponent,
    projectsRoute,
    projectsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...projectsRoute,
    ...projectsPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ProjectsComponent,
        ProjectsDetailComponent,
        ProjectsDialogComponent,
        ProjectsDeleteDialogComponent,
        ProjectsPopupComponent,
        ProjectsDeletePopupComponent,
    ],
    entryComponents: [
        ProjectsComponent,
        ProjectsDialogComponent,
        ProjectsPopupComponent,
        ProjectsDeleteDialogComponent,
        ProjectsDeletePopupComponent,
    ],
    providers: [
        ProjectsService,
        ProjectsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationProjectsModule {}
