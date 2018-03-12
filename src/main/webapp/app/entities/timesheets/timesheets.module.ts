import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    TimesheetsService,
    TimesheetsPopupService,
    TimesheetsComponent,
    TimesheetsDetailComponent,
    TimesheetsDialogComponent,
    TimesheetsPopupComponent,
    TimesheetsDeletePopupComponent,
    TimesheetsDeleteDialogComponent,
    timesheetsRoute,
    timesheetsPopupRoute,
    TimesheetsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...timesheetsRoute,
    ...timesheetsPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TimesheetsComponent,
        TimesheetsDetailComponent,
        TimesheetsDialogComponent,
        TimesheetsDeleteDialogComponent,
        TimesheetsPopupComponent,
        TimesheetsDeletePopupComponent,
    ],
    entryComponents: [
        TimesheetsComponent,
        TimesheetsDialogComponent,
        TimesheetsPopupComponent,
        TimesheetsDeleteDialogComponent,
        TimesheetsDeletePopupComponent,
    ],
    providers: [
        TimesheetsService,
        TimesheetsPopupService,
        TimesheetsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationTimesheetsModule {}
