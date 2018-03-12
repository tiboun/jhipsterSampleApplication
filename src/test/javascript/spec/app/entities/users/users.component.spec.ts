/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { UsersComponent } from '../../../../../../main/webapp/app/entities/users/users.component';
import { UsersService } from '../../../../../../main/webapp/app/entities/users/users.service';
import { Users } from '../../../../../../main/webapp/app/entities/users/users.model';

describe('Component Tests', () => {

    describe('Users Management Component', () => {
        let comp: UsersComponent;
        let fixture: ComponentFixture<UsersComponent>;
        let service: UsersService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [UsersComponent],
                providers: [
                    UsersService
                ]
            })
            .overrideTemplate(UsersComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UsersComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UsersService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Users(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.users[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
