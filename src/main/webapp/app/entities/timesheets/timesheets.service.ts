import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Timesheets } from './timesheets.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Timesheets>;

@Injectable()
export class TimesheetsService {

    private resourceUrl =  SERVER_API_URL + 'api/timesheets';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(timesheets: Timesheets): Observable<EntityResponseType> {
        const copy = this.convert(timesheets);
        return this.http.post<Timesheets>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(timesheets: Timesheets): Observable<EntityResponseType> {
        const copy = this.convert(timesheets);
        return this.http.put<Timesheets>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Timesheets>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Timesheets[]>> {
        const options = createRequestOption(req);
        return this.http.get<Timesheets[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Timesheets[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Timesheets = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Timesheets[]>): HttpResponse<Timesheets[]> {
        const jsonResponse: Timesheets[] = res.body;
        const body: Timesheets[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Timesheets.
     */
    private convertItemFromServer(timesheets: Timesheets): Timesheets {
        const copy: Timesheets = Object.assign({}, timesheets);
        copy.creationDate = this.dateUtils
            .convertDateTimeFromServer(timesheets.creationDate);
        return copy;
    }

    /**
     * Convert a Timesheets to a JSON which can be sent to the server.
     */
    private convert(timesheets: Timesheets): Timesheets {
        const copy: Timesheets = Object.assign({}, timesheets);

        copy.creationDate = this.dateUtils.toDate(timesheets.creationDate);
        return copy;
    }
}
