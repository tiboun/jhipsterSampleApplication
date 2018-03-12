import { BaseEntity } from './../../shared';

export class Users implements BaseEntity {
    constructor(
        public id?: number,
        public firstname?: string,
        public lastname?: string,
        public email?: string,
        public creationDate?: any,
        public lastLoginDate?: any,
        public timesheets?: BaseEntity[],
    ) {
    }
}
