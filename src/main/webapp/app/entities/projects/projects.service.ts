import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Projects } from './projects.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Projects>;

@Injectable()
export class ProjectsService {

    private resourceUrl =  SERVER_API_URL + 'api/projects';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(projects: Projects): Observable<EntityResponseType> {
        const copy = this.convert(projects);
        return this.http.post<Projects>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(projects: Projects): Observable<EntityResponseType> {
        const copy = this.convert(projects);
        return this.http.put<Projects>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Projects>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Projects[]>> {
        const options = createRequestOption(req);
        return this.http.get<Projects[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Projects[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Projects = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Projects[]>): HttpResponse<Projects[]> {
        const jsonResponse: Projects[] = res.body;
        const body: Projects[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Projects.
     */
    private convertItemFromServer(projects: Projects): Projects {
        const copy: Projects = Object.assign({}, projects);
        copy.creationDate = this.dateUtils
            .convertDateTimeFromServer(projects.creationDate);
        return copy;
    }

    /**
     * Convert a Projects to a JSON which can be sent to the server.
     */
    private convert(projects: Projects): Projects {
        const copy: Projects = Object.assign({}, projects);

        copy.creationDate = this.dateUtils.toDate(projects.creationDate);
        return copy;
    }
}
