import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Users } from './users.model';
import { UsersPopupService } from './users-popup.service';
import { UsersService } from './users.service';

@Component({
    selector: 'jhi-users-dialog',
    templateUrl: './users-dialog.component.html'
})
export class UsersDialogComponent implements OnInit {

    users: Users;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private usersService: UsersService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.users.id !== undefined) {
            this.subscribeToSaveResponse(
                this.usersService.update(this.users));
        } else {
            this.subscribeToSaveResponse(
                this.usersService.create(this.users));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Users>>) {
        result.subscribe((res: HttpResponse<Users>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Users) {
        this.eventManager.broadcast({ name: 'usersListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-users-popup',
    template: ''
})
export class UsersPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private usersPopupService: UsersPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.usersPopupService
                    .open(UsersDialogComponent as Component, params['id']);
            } else {
                this.usersPopupService
                    .open(UsersDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
