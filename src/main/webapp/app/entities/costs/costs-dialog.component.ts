import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Costs } from './costs.model';
import { CostsPopupService } from './costs-popup.service';
import { CostsService } from './costs.service';
import { Activities, ActivitiesService } from '../activities';

@Component({
    selector: 'jhi-costs-dialog',
    templateUrl: './costs-dialog.component.html'
})
export class CostsDialogComponent implements OnInit {

    costs: Costs;
    isSaving: boolean;

    activities: Activities[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private costsService: CostsService,
        private activitiesService: ActivitiesService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.activitiesService.query()
            .subscribe((res: HttpResponse<Activities[]>) => { this.activities = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.costs.id !== undefined) {
            this.subscribeToSaveResponse(
                this.costsService.update(this.costs));
        } else {
            this.subscribeToSaveResponse(
                this.costsService.create(this.costs));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Costs>>) {
        result.subscribe((res: HttpResponse<Costs>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Costs) {
        this.eventManager.broadcast({ name: 'costsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackActivitiesById(index: number, item: Activities) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-costs-popup',
    template: ''
})
export class CostsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private costsPopupService: CostsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.costsPopupService
                    .open(CostsDialogComponent as Component, params['id']);
            } else {
                this.costsPopupService
                    .open(CostsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
