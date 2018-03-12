import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Costs } from './costs.model';
import { CostsService } from './costs.service';

@Component({
    selector: 'jhi-costs-detail',
    templateUrl: './costs-detail.component.html'
})
export class CostsDetailComponent implements OnInit, OnDestroy {

    costs: Costs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private costsService: CostsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCosts();
    }

    load(id) {
        this.costsService.find(id)
            .subscribe((costsResponse: HttpResponse<Costs>) => {
                this.costs = costsResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCosts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'costsListModification',
            (response) => this.load(this.costs.id)
        );
    }
}
