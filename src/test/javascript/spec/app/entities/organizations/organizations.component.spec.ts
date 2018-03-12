/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { OrganizationsComponent } from '../../../../../../main/webapp/app/entities/organizations/organizations.component';
import { OrganizationsService } from '../../../../../../main/webapp/app/entities/organizations/organizations.service';
import { Organizations } from '../../../../../../main/webapp/app/entities/organizations/organizations.model';

describe('Component Tests', () => {

    describe('Organizations Management Component', () => {
        let comp: OrganizationsComponent;
        let fixture: ComponentFixture<OrganizationsComponent>;
        let service: OrganizationsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [OrganizationsComponent],
                providers: [
                    OrganizationsService
                ]
            })
            .overrideTemplate(OrganizationsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrganizationsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrganizationsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Organizations(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.organizations[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
