import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    CostsService,
    CostsPopupService,
    CostsComponent,
    CostsDetailComponent,
    CostsDialogComponent,
    CostsPopupComponent,
    CostsDeletePopupComponent,
    CostsDeleteDialogComponent,
    costsRoute,
    costsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...costsRoute,
    ...costsPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CostsComponent,
        CostsDetailComponent,
        CostsDialogComponent,
        CostsDeleteDialogComponent,
        CostsPopupComponent,
        CostsDeletePopupComponent,
    ],
    entryComponents: [
        CostsComponent,
        CostsDialogComponent,
        CostsPopupComponent,
        CostsDeleteDialogComponent,
        CostsDeletePopupComponent,
    ],
    providers: [
        CostsService,
        CostsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationCostsModule {}
