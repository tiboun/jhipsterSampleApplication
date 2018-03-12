import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Organizations } from './organizations.model';
import { OrganizationsService } from './organizations.service';

@Component({
    selector: 'jhi-organizations-detail',
    templateUrl: './organizations-detail.component.html'
})
export class OrganizationsDetailComponent implements OnInit, OnDestroy {

    organizations: Organizations;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private organizationsService: OrganizationsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrganizations();
    }

    load(id) {
        this.organizationsService.find(id)
            .subscribe((organizationsResponse: HttpResponse<Organizations>) => {
                this.organizations = organizationsResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOrganizations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'organizationsListModification',
            (response) => this.load(this.organizations.id)
        );
    }
}
