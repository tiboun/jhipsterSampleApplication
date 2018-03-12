import { BaseEntity } from './../../shared';

export class Timesheets implements BaseEntity {
    constructor(
        public id?: number,
        public month?: number,
        public year?: number,
        public creationDate?: any,
        public usersId?: number,
        public userId?: number,
        public activities?: BaseEntity[],
    ) {
    }
}
