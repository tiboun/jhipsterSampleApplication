import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterSampleApplicationUsersModule } from './users/users.module';
import { JhipsterSampleApplicationTimesheetsModule } from './timesheets/timesheets.module';
import { JhipsterSampleApplicationOrganizationsModule } from './organizations/organizations.module';
import { JhipsterSampleApplicationProjectsModule } from './projects/projects.module';
import { JhipsterSampleApplicationActivitiesModule } from './activities/activities.module';
import { JhipsterSampleApplicationCostsModule } from './costs/costs.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JhipsterSampleApplicationUsersModule,
        JhipsterSampleApplicationTimesheetsModule,
        JhipsterSampleApplicationOrganizationsModule,
        JhipsterSampleApplicationProjectsModule,
        JhipsterSampleApplicationActivitiesModule,
        JhipsterSampleApplicationCostsModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationEntityModule {}
