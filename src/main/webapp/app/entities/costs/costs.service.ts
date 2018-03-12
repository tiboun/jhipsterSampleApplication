import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Costs } from './costs.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Costs>;

@Injectable()
export class CostsService {

    private resourceUrl =  SERVER_API_URL + 'api/costs';

    constructor(private http: HttpClient) { }

    create(costs: Costs): Observable<EntityResponseType> {
        const copy = this.convert(costs);
        return this.http.post<Costs>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(costs: Costs): Observable<EntityResponseType> {
        const copy = this.convert(costs);
        return this.http.put<Costs>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Costs>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Costs[]>> {
        const options = createRequestOption(req);
        return this.http.get<Costs[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Costs[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Costs = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Costs[]>): HttpResponse<Costs[]> {
        const jsonResponse: Costs[] = res.body;
        const body: Costs[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Costs.
     */
    private convertItemFromServer(costs: Costs): Costs {
        const copy: Costs = Object.assign({}, costs);
        return copy;
    }

    /**
     * Convert a Costs to a JSON which can be sent to the server.
     */
    private convert(costs: Costs): Costs {
        const copy: Costs = Object.assign({}, costs);
        return copy;
    }
}
