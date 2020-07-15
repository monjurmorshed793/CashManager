import { Moment } from 'moment';

export interface IPayTo {
  id?: number;
  name?: string;
  description?: any;
  createdBy?: string;
  createdOn?: Moment;
  modifiedBy?: string;
  modifiedOn?: Moment;
}

export class PayTo implements IPayTo {
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
