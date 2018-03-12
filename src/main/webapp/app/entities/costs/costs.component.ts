import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Costs } from './costs.model';
import { CostsService } from './costs.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-costs',
    templateUrl: './costs.component.html'
})
export class CostsComponent implements OnInit, OnDestroy {
costs: Costs[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private costsService: CostsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.costsService.query().subscribe(
            (res: HttpResponse<Costs[]>) => {
                this.costs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCosts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Costs) {
        return item.id;
    }
    registerChangeInCosts() {
        this.eventSubscriber = this.eventManager.subscribe('costsListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
