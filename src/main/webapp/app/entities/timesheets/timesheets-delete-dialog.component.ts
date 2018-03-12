import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Timesheets } from './timesheets.model';
import { TimesheetsPopupService } from './timesheets-popup.service';
import { TimesheetsService } from './timesheets.service';

@Component({
    selector: 'jhi-timesheets-delete-dialog',
    templateUrl: './timesheets-delete-dialog.component.html'
})
export class TimesheetsDeleteDialogComponent {

    timesheets: Timesheets;

    constructor(
        private timesheetsService: TimesheetsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.timesheetsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'timesheetsListModification',
                content: 'Deleted an timesheets'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-timesheets-delete-popup',
    template: ''
})
export class TimesheetsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private timesheetsPopupService: TimesheetsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.timesheetsPopupService
                .open(TimesheetsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
