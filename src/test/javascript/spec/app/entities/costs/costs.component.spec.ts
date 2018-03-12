/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CostsComponent } from '../../../../../../main/webapp/app/entities/costs/costs.component';
import { CostsService } from '../../../../../../main/webapp/app/entities/costs/costs.service';
import { Costs } from '../../../../../../main/webapp/app/entities/costs/costs.model';

describe('Component Tests', () => {

    describe('Costs Management Component', () => {
        let comp: CostsComponent;
        let fixture: ComponentFixture<CostsComponent>;
        let service: CostsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [CostsComponent],
                providers: [
                    CostsService
                ]
            })
            .overrideTemplate(CostsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CostsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CostsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Costs(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.costs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
