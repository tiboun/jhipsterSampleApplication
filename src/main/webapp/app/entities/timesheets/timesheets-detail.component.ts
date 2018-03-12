import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Timesheets } from './timesheets.model';
import { TimesheetsService } from './timesheets.service';

@Component({
    selector: 'jhi-timesheets-detail',
    templateUrl: './timesheets-detail.component.html'
})
export class TimesheetsDetailComponent implements OnInit, OnDestroy {

    timesheets: Timesheets;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private timesheetsService: TimesheetsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTimesheets();
    }

    load(id) {
        this.timesheetsService.find(id)
            .subscribe((timesheetsResponse: HttpResponse<Timesheets>) => {
                this.timesheets = timesheetsResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTimesheets() {
        this.eventSubscriber = this.eventManager.subscribe(
            'timesheetsListModification',
            (response) => this.load(this.timesheets.id)
        );
    }
}
