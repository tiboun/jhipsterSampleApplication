import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Activities } from './activities.model';
import { ActivitiesPopupService } from './activities-popup.service';
import { ActivitiesService } from './activities.service';
import { Timesheets, TimesheetsService } from '../timesheets';
import { Projects, ProjectsService } from '../projects';

@Component({
    selector: 'jhi-activities-dialog',
    templateUrl: './activities-dialog.component.html'
})
export class ActivitiesDialogComponent implements OnInit {

    activities: Activities;
    isSaving: boolean;

    timesheets: Timesheets[];

    projects: Projects[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private activitiesService: ActivitiesService,
        private timesheetsService: TimesheetsService,
        private projectsService: ProjectsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.timesheetsService.query()
            .subscribe((res: HttpResponse<Timesheets[]>) => { this.timesheets = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.projectsService
            .query({filter: 'activities-is-null'})
            .subscribe((res: HttpResponse<Projects[]>) => {
                if (!this.activities.projectId) {
                    this.projects = res.body;
                } else {
                    this.projectsService
                        .find(this.activities.projectId)
                        .subscribe((subRes: HttpResponse<Projects>) => {
                            this.projects = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.activities.id !== undefined) {
            this.subscribeToSaveResponse(
                this.activitiesService.update(this.activities));
        } else {
            this.subscribeToSaveResponse(
                this.activitiesService.create(this.activities));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Activities>>) {
        result.subscribe((res: HttpResponse<Activities>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Activities) {
        this.eventManager.broadcast({ name: 'activitiesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTimesheetsById(index: number, item: Timesheets) {
        return item.id;
    }

    trackProjectsById(index: number, item: Projects) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-activities-popup',
    template: ''
})
export class ActivitiesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private activitiesPopupService: ActivitiesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.activitiesPopupService
                    .open(ActivitiesDialogComponent as Component, params['id']);
            } else {
                this.activitiesPopupService
                    .open(ActivitiesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
