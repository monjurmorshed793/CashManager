import { Moment } from 'moment';
import { IExpanseDtl } from 'app/shared/model/expanse-dtl.model';
import { MonthType } from 'app/shared/model/enumerations/month-type.model';

export interface IExpanse {
  id?: number;
  loginId?: string;
  voucherNo?: number;
  voucherDate?: Moment;
  month?: MonthType;
  notes?: any;
  totalAmount?: number;
  itemNames?: any;
  isPosted?: boolean;
  postDate?: Moment;
  createdBy?: string;
  createdOn?: Moment;
  modifiedBy?: string;
  modifiedOn?: Moment;
  expanseDtls?: IExpanseDtl[];
  payToName?: string;
  payToId?: number;
}

export class Expanse implements IExpanse {
  constructor(
    public id?: number,
    public loginId?: string,
    public voucherNo?: number,
    public voucherDate?: Moment,
    public month?: MonthType,
    public notes?: any,
    public totalAmount?: number,
    public itemNames?: any,
    public isPosted?: boolean,
    public postDate?: Moment,
    public createdBy?: string,
    public createdOn?: Moment,
    public modifiedBy?: string,
    public modifiedOn?: Moment,
    public expanseDtls?: IExpanseDtl[],
    public payToName?: string,
    public payToId?: number
  ) {
    this.isPosted = this.isPosted || false;
  }
}
