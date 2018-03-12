import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Projects } from './projects.model';
import { ProjectsService } from './projects.service';

@Component({
    selector: 'jhi-projects-detail',
    templateUrl: './projects-detail.component.html'
})
export class ProjectsDetailComponent implements OnInit, OnDestroy {

    projects: Projects;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private projectsService: ProjectsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProjects();
    }

    load(id) {
        this.projectsService.find(id)
            .subscribe((projectsResponse: HttpResponse<Projects>) => {
                this.projects = projectsResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProjects() {
        this.eventSubscriber = this.eventManager.subscribe(
            'projectsListModification',
            (response) => this.load(this.projects.id)
        );
    }
}
