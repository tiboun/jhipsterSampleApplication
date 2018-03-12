import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Organizations } from './organizations.model';
import { OrganizationsPopupService } from './organizations-popup.service';
import { OrganizationsService } from './organizations.service';

@Component({
    selector: 'jhi-organizations-delete-dialog',
    templateUrl: './organizations-delete-dialog.component.html'
})
export class OrganizationsDeleteDialogComponent {

    organizations: Organizations;

    constructor(
        private organizationsService: OrganizationsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.organizationsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'organizationsListModification',
                content: 'Deleted an organizations'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-organizations-delete-popup',
    template: ''
})
export class OrganizationsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private organizationsPopupService: OrganizationsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.organizationsPopupService
                .open(OrganizationsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
