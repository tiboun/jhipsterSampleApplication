import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Users } from './users.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Users>;

@Injectable()
export class UsersService {

    private resourceUrl =  SERVER_API_URL + 'api/users';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(users: Users): Observable<EntityResponseType> {
        const copy = this.convert(users);
        return this.http.post<Users>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(users: Users): Observable<EntityResponseType> {
        const copy = this.convert(users);
        return this.http.put<Users>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Users>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Users[]>> {
        const options = createRequestOption(req);
        return this.http.get<Users[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Users[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Users = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Users[]>): HttpResponse<Users[]> {
        const jsonResponse: Users[] = res.body;
        const body: Users[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Users.
     */
    private convertItemFromServer(users: Users): Users {
        const copy: Users = Object.assign({}, users);
        copy.creationDate = this.dateUtils
            .convertDateTimeFromServer(users.creationDate);
        copy.lastLoginDate = this.dateUtils
            .convertDateTimeFromServer(users.lastLoginDate);
        return copy;
    }

    /**
     * Convert a Users to a JSON which can be sent to the server.
     */
    private convert(users: Users): Users {
        const copy: Users = Object.assign({}, users);

        copy.creationDate = this.dateUtils.toDate(users.creationDate);

        copy.lastLoginDate = this.dateUtils.toDate(users.lastLoginDate);
        return copy;
    }
}
