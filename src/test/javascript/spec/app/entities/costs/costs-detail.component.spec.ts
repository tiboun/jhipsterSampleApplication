/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CostsDetailComponent } from '../../../../../../main/webapp/app/entities/costs/costs-detail.component';
import { CostsService } from '../../../../../../main/webapp/app/entities/costs/costs.service';
import { Costs } from '../../../../../../main/webapp/app/entities/costs/costs.model';

describe('Component Tests', () => {

    describe('Costs Management Detail Component', () => {
        let comp: CostsDetailComponent;
        let fixture: ComponentFixture<CostsDetailComponent>;
        let service: CostsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [CostsDetailComponent],
                providers: [
                    CostsService
                ]
            })
            .overrideTemplate(CostsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CostsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CostsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Costs(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.costs).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
