import { BaseEntity } from './../../shared';

export class Costs implements BaseEntity {
    constructor(
        public id?: number,
        public dayOfMonth?: number,
        public spentDays?: number,
        public activitiesId?: number,
    ) {
    }
}
