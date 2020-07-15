import { Moment } from 'moment';
import { DepositMedium } from 'app/shared/model/enumerations/deposit-medium.model';

export interface IDeposit {
  id?: number;
  loginId?: string;
  depositNo?: number;
  depositBy?: string;
  depositDate?: Moment;
  medium?: DepositMedium;
  amount?: number;
  isPosted?: boolean;
  postDate?: Moment;
  createdBy?: string;
  createdOn?: Moment;
  modifiedBy?: string;
  modifiedOn?: Moment;
}

export class Deposit implements IDeposit {
  constructor(
    public id?: number,
    public loginId?: string,
    public depositNo?: number,
    public depositBy?: string,
    public depositDate?: Moment,
    public medium?: DepositMedium,
    public amount?: number,
    public isPosted?: boolean,
    public postDate?: Moment,
    public createdBy?: string,
    public createdOn?: Moment,
    public modifiedBy?: string,
    public modifiedOn?: Moment
  ) {
    this.isPosted = this.isPosted || false;
  }
}
