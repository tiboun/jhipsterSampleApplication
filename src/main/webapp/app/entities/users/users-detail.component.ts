import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Users } from './users.model';
import { UsersService } from './users.service';

@Component({
    selector: 'jhi-users-detail',
    templateUrl: './users-detail.component.html'
})
export class UsersDetailComponent implements OnInit, OnDestroy {

    users: Users;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private usersService: UsersService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUsers();
    }

    load(id) {
        this.usersService.find(id)
            .subscribe((usersResponse: HttpResponse<Users>) => {
                this.users = usersResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUsers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'usersListModification',
            (response) => this.load(this.users.id)
        );
    }
}
