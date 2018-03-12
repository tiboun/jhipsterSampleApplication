import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Timesheets } from './timesheets.model';
import { TimesheetsService } from './timesheets.service';

@Injectable()
export class TimesheetsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private timesheetsService: TimesheetsService

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
                this.timesheetsService.find(id)
                    .subscribe((timesheetsResponse: HttpResponse<Timesheets>) => {
                        const timesheets: Timesheets = timesheetsResponse.body;
                        timesheets.creationDate = this.datePipe
                            .transform(timesheets.creationDate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.timesheetsModalRef(component, timesheets);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.timesheetsModalRef(component, new Timesheets());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    timesheetsModalRef(component: Component, timesheets: Timesheets): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.timesheets = timesheets;
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
