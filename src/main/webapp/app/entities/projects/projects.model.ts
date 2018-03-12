import { BaseEntity } from './../../shared';

export class Projects implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public creationDate?: any,
        public organizationsId?: number,
        public organizationId?: number,
    ) {
    }
}
