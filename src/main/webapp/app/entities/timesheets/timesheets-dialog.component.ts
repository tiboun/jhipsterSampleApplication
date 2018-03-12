import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Timesheets } from './timesheets.model';
import { TimesheetsPopupService } from './timesheets-popup.service';
import { TimesheetsService } from './timesheets.service';
import { Users, UsersService } from '../users';

@Component({
    selector: 'jhi-timesheets-dialog',
    templateUrl: './timesheets-dialog.component.html'
})
export class TimesheetsDialogComponent implements OnInit {

    timesheets: Timesheets;
    isSaving: boolean;

    users: Users[];

    users: Users[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private timesheetsService: TimesheetsService,
        private usersService: UsersService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.usersService.query()
            .subscribe((res: HttpResponse<Users[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.usersService
            .query({filter: 'timesheets-is-null'})
            .subscribe((res: HttpResponse<Users[]>) => {
                if (!this.timesheets.userId) {
                    this.users = res.body;
                } else {
                    this.usersService
                        .find(this.timesheets.userId)
                        .subscribe((subRes: HttpResponse<Users>) => {
                            this.users = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.timesheets.id !== undefined) {
            this.subscribeToSaveResponse(
                this.timesheetsService.update(this.timesheets));
        } else {
            this.subscribeToSaveResponse(
                this.timesheetsService.create(this.timesheets));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Timesheets>>) {
        result.subscribe((res: HttpResponse<Timesheets>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Timesheets) {
        this.eventManager.broadcast({ name: 'timesheetsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUsersById(index: number, item: Users) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-timesheets-popup',
    template: ''
})
export class TimesheetsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private timesheetsPopupService: TimesheetsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.timesheetsPopupService
                    .open(TimesheetsDialogComponent as Component, params['id']);
            } else {
                this.timesheetsPopupService
                    .open(TimesheetsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
