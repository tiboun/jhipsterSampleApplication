/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { OrganizationsDetailComponent } from '../../../../../../main/webapp/app/entities/organizations/organizations-detail.component';
import { OrganizationsService } from '../../../../../../main/webapp/app/entities/organizations/organizations.service';
import { Organizations } from '../../../../../../main/webapp/app/entities/organizations/organizations.model';

describe('Component Tests', () => {

    describe('Organizations Management Detail Component', () => {
        let comp: OrganizationsDetailComponent;
        let fixture: ComponentFixture<OrganizationsDetailComponent>;
        let service: OrganizationsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [OrganizationsDetailComponent],
                providers: [
                    OrganizationsService
                ]
            })
            .overrideTemplate(OrganizationsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrganizationsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrganizationsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Organizations(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.organizations).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
