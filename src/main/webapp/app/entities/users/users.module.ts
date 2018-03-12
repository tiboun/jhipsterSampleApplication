import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    UsersService,
    UsersPopupService,
    UsersComponent,
    UsersDetailComponent,
    UsersDialogComponent,
    UsersPopupComponent,
    UsersDeletePopupComponent,
    UsersDeleteDialogComponent,
    usersRoute,
    usersPopupRoute,
} from './';

const ENTITY_STATES = [
    ...usersRoute,
    ...usersPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        UsersComponent,
        UsersDetailComponent,
        UsersDialogComponent,
        UsersDeleteDialogComponent,
        UsersPopupComponent,
        UsersDeletePopupComponent,
    ],
    entryComponents: [
        UsersComponent,
        UsersDialogComponent,
        UsersPopupComponent,
        UsersDeleteDialogComponent,
        UsersDeletePopupComponent,
    ],
    providers: [
        UsersService,
        UsersPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationUsersModule {}
