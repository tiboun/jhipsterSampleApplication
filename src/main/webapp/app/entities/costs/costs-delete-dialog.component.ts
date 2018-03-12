import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Costs } from './costs.model';
import { CostsPopupService } from './costs-popup.service';
import { CostsService } from './costs.service';

@Component({
    selector: 'jhi-costs-delete-dialog',
    templateUrl: './costs-delete-dialog.component.html'
})
export class CostsDeleteDialogComponent {

    costs: Costs;

    constructor(
        private costsService: CostsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.costsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'costsListModification',
                content: 'Deleted an costs'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-costs-delete-popup',
    template: ''
})
export class CostsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private costsPopupService: CostsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.costsPopupService
                .open(CostsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
