import { Moment } from 'moment';

export interface IItem {
  id?: number;
  name?: string;
  description?: any;
  createdBy?: string;
  createdOn?: Moment;
  modifiedBy?: string;
  modifiedOn?: Moment;
}

export class Item implements IItem {
  constructor(
    public id?: number,
    public name?: string,
    public description?: any,
    public createdBy?: string,
    public createdOn?: Moment,
    public modifiedBy?: string,
    public modifiedOn?: Moment
  ) {}
}
