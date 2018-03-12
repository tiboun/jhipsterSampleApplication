import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Projects } from './projects.model';
import { ProjectsService } from './projects.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-projects',
    templateUrl: './projects.component.html'
})
export class ProjectsComponent implements OnInit, OnDestroy {
projects: Projects[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private projectsService: ProjectsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.projectsService.query().subscribe(
            (res: HttpResponse<Projects[]>) => {
                this.projects = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInProjects();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Projects) {
        return item.id;
    }
    registerChangeInProjects() {
        this.eventSubscriber = this.eventManager.subscribe('projectsListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
