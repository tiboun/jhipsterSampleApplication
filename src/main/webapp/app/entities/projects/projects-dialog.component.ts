import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Projects } from './projects.model';
import { ProjectsPopupService } from './projects-popup.service';
import { ProjectsService } from './projects.service';
import { Organizations, OrganizationsService } from '../organizations';

@Component({
    selector: 'jhi-projects-dialog',
    templateUrl: './projects-dialog.component.html'
})
export class ProjectsDialogComponent implements OnInit {

    projects: Projects;
    isSaving: boolean;

    organizations: Organizations[];

    organizations: Organizations[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private projectsService: ProjectsService,
        private organizationsService: OrganizationsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.organizationsService.query()
            .subscribe((res: HttpResponse<Organizations[]>) => { this.organizations = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.organizationsService
            .query({filter: 'projects-is-null'})
            .subscribe((res: HttpResponse<Organizations[]>) => {
                if (!this.projects.organizationId) {
                    this.organizations = res.body;
                } else {
                    this.organizationsService
                        .find(this.projects.organizationId)
                        .subscribe((subRes: HttpResponse<Organizations>) => {
                            this.organizations = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.projects.id !== undefined) {
            this.subscribeToSaveResponse(
                this.projectsService.update(this.projects));
        } else {
            this.subscribeToSaveResponse(
                this.projectsService.create(this.projects));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Projects>>) {
        result.subscribe((res: HttpResponse<Projects>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Projects) {
        this.eventManager.broadcast({ name: 'projectsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackOrganizationsById(index: number, item: Organizations) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-projects-popup',
    template: ''
})
export class ProjectsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private projectsPopupService: ProjectsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.projectsPopupService
                    .open(ProjectsDialogComponent as Component, params['id']);
            } else {
                this.projectsPopupService
                    .open(ProjectsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
