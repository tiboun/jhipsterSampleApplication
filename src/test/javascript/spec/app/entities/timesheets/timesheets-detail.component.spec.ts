/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TimesheetsDetailComponent } from '../../../../../../main/webapp/app/entities/timesheets/timesheets-detail.component';
import { TimesheetsService } from '../../../../../../main/webapp/app/entities/timesheets/timesheets.service';
import { Timesheets } from '../../../../../../main/webapp/app/entities/timesheets/timesheets.model';

describe('Component Tests', () => {

    describe('Timesheets Management Detail Component', () => {
        let comp: TimesheetsDetailComponent;
        let fixture: ComponentFixture<TimesheetsDetailComponent>;
        let service: TimesheetsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [TimesheetsDetailComponent],
                providers: [
                    TimesheetsService
                ]
            })
            .overrideTemplate(TimesheetsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TimesheetsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TimesheetsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Timesheets(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.timesheets).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
