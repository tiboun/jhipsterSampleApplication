import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Organizations } from './organizations.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Organizations>;

@Injectable()
export class OrganizationsService {

    private resourceUrl =  SERVER_API_URL + 'api/organizations';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(organizations: Organizations): Observable<EntityResponseType> {
        const copy = this.convert(organizations);
        return this.http.post<Organizations>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(organizations: Organizations): Observable<EntityResponseType> {
        const copy = this.convert(organizations);
        return this.http.put<Organizations>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Organizations>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Organizations[]>> {
        const options = createRequestOption(req);
        return this.http.get<Organizations[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Organizations[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Organizations = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Organizations[]>): HttpResponse<Organizations[]> {
        const jsonResponse: Organizations[] = res.body;
        const body: Organizations[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Organizations.
     */
    private convertItemFromServer(organizations: Organizations): Organizations {
        const copy: Organizations = Object.assign({}, organizations);
        copy.creationDate = this.dateUtils
            .convertDateTimeFromServer(organizations.creationDate);
        return copy;
    }

    /**
     * Convert a Organizations to a JSON which can be sent to the server.
     */
    private convert(organizations: Organizations): Organizations {
        const copy: Organizations = Object.assign({}, organizations);

        copy.creationDate = this.dateUtils.toDate(organizations.creationDate);
        return copy;
    }
}
