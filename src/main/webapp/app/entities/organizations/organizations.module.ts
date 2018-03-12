import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    OrganizationsService,
    OrganizationsPopupService,
    OrganizationsComponent,
    OrganizationsDetailComponent,
    OrganizationsDialogComponent,
    OrganizationsPopupComponent,
    OrganizationsDeletePopupComponent,
    OrganizationsDeleteDialogComponent,
    organizationsRoute,
    organizationsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...organizationsRoute,
    ...organizationsPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OrganizationsComponent,
        OrganizationsDetailComponent,
        OrganizationsDialogComponent,
        OrganizationsDeleteDialogComponent,
        OrganizationsPopupComponent,
        OrganizationsDeletePopupComponent,
    ],
    entryComponents: [
        OrganizationsComponent,
        OrganizationsDialogComponent,
        OrganizationsPopupComponent,
        OrganizationsDeleteDialogComponent,
        OrganizationsDeletePopupComponent,
    ],
    providers: [
        OrganizationsService,
        OrganizationsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationOrganizationsModule {}
