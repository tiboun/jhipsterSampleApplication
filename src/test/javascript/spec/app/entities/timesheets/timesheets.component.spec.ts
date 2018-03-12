/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TimesheetsComponent } from '../../../../../../main/webapp/app/entities/timesheets/timesheets.component';
import { TimesheetsService } from '../../../../../../main/webapp/app/entities/timesheets/timesheets.service';
import { Timesheets } from '../../../../../../main/webapp/app/entities/timesheets/timesheets.model';

describe('Component Tests', () => {

    describe('Timesheets Management Component', () => {
        let comp: TimesheetsComponent;
        let fixture: ComponentFixture<TimesheetsComponent>;
        let service: TimesheetsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [TimesheetsComponent],
                providers: [
                    TimesheetsService
                ]
            })
            .overrideTemplate(TimesheetsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TimesheetsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TimesheetsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Timesheets(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.timesheets[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
