import { BaseEntity } from './../../shared';

export class Activities implements BaseEntity {
    constructor(
        public id?: number,
        public timesheetsId?: number,
        public projectId?: number,
        public costs?: BaseEntity[],
    ) {
    }
}
