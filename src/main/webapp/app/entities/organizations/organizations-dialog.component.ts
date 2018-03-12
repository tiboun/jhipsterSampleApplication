import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Organizations } from './organizations.model';
import { OrganizationsPopupService } from './organizations-popup.service';
import { OrganizationsService } from './organizations.service';

@Component({
    selector: 'jhi-organizations-dialog',
    templateUrl: './organizations-dialog.component.html'
})
export class OrganizationsDialogComponent implements OnInit {

    organizations: Organizations;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private organizationsService: OrganizationsService,
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
        if (this.organizations.id !== undefined) {
            this.subscribeToSaveResponse(
                this.organizationsService.update(this.organizations));
        } else {
            this.subscribeToSaveResponse(
                this.organizationsService.create(this.organizations));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Organizations>>) {
        result.subscribe((res: HttpResponse<Organizations>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Organizations) {
        this.eventManager.broadcast({ name: 'organizationsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-organizations-popup',
    template: ''
})
export class OrganizationsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private organizationsPopupService: OrganizationsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.organizationsPopupService
                    .open(OrganizationsDialogComponent as Component, params['id']);
            } else {
                this.organizationsPopupService
                    .open(OrganizationsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
