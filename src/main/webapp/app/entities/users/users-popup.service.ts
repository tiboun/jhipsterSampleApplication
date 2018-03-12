import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Users } from './users.model';
import { UsersService } from './users.service';

@Injectable()
export class UsersPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private usersService: UsersService

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
                this.usersService.find(id)
                    .subscribe((usersResponse: HttpResponse<Users>) => {
                        const users: Users = usersResponse.body;
                        users.creationDate = this.datePipe
                            .transform(users.creationDate, 'yyyy-MM-ddTHH:mm:ss');
                        users.lastLoginDate = this.datePipe
                            .transform(users.lastLoginDate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.usersModalRef(component, users);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.usersModalRef(component, new Users());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    usersModalRef(component: Component, users: Users): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.users = users;
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
