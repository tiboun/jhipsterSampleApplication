/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { UsersDetailComponent } from '../../../../../../main/webapp/app/entities/users/users-detail.component';
import { UsersService } from '../../../../../../main/webapp/app/entities/users/users.service';
import { Users } from '../../../../../../main/webapp/app/entities/users/users.model';

describe('Component Tests', () => {

    describe('Users Management Detail Component', () => {
        let comp: UsersDetailComponent;
        let fixture: ComponentFixture<UsersDetailComponent>;
        let service: UsersService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [UsersDetailComponent],
                providers: [
                    UsersService
                ]
            })
            .overrideTemplate(UsersDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UsersDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UsersService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Users(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.users).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
