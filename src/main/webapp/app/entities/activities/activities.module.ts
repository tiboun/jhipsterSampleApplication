import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    ActivitiesService,
    ActivitiesPopupService,
    ActivitiesComponent,
    ActivitiesDetailComponent,
    ActivitiesDialogComponent,
    ActivitiesPopupComponent,
    ActivitiesDeletePopupComponent,
    ActivitiesDeleteDialogComponent,
    activitiesRoute,
    activitiesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...activitiesRoute,
    ...activitiesPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ActivitiesComponent,
        ActivitiesDetailComponent,
        ActivitiesDialogComponent,
        ActivitiesDeleteDialogComponent,
        ActivitiesPopupComponent,
        ActivitiesDeletePopupComponent,
    ],
    entryComponents: [
        ActivitiesComponent,
        ActivitiesDialogComponent,
        ActivitiesPopupComponent,
        ActivitiesDeleteDialogComponent,
        ActivitiesDeletePopupComponent,
    ],
    providers: [
        ActivitiesService,
        ActivitiesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationActivitiesModule {}
