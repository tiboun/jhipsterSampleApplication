import { BaseEntity } from './../../shared';

export class Organizations implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public creationDate?: any,
        public projects?: BaseEntity[],
    ) {
    }
}
