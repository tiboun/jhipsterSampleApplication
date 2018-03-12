import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Organizations } from './organizations.model';
import { OrganizationsService } from './organizations.service';

@Injectable()
export class OrganizationsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private organizationsService: OrganizationsService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.organizationsService.find(id)
                    .subscribe((organizationsResponse: HttpResponse<Organizations>) => {
                        const organizations: Organizations = organizationsResponse.body;
                        organizations.creationDate = this.datePipe
                            .transform(organizations.creationDate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.organizationsModalRef(component, organizations);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.organizationsModalRef(component, new Organizations());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    organizationsModalRef(component: Component, organizations: Organizations): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.organizations = organizations;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
