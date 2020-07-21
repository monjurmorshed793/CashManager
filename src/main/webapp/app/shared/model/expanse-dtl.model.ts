import { Moment } from 'moment';

export interface IExpanseDtl {
  id?: number;
  quantity?: number;
  unitPrice?: number;
  amount?: number;
  createdBy?: string;
  createdOn?: Moment;
  modifiedBy?: string;
  modifiedOn?: Moment;
  itemName?: string;
  itemId?: number;
  expanseVoucherNo?: string;
  expanseId?: number;
}

export class ExpanseDtl implements IExpanseDtl {
  constructor(
    public id?: number,
    public quantity?: number,
    public unitPrice?: number,
    public amount?: number,
    public createdBy?: string,
    public createdOn?: Moment,
    public modifiedBy?: string,
    public modifiedOn?: Moment,
    public itemName?: string,
    public itemId?: number,
    public expanseVoucherNo?: string,
    public expanseId?: number
  ) {}
}
