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
    public createdBy?: string,
    public createdOn?: Moment,
    public modifiedBy?: string,
    public modifiedOn?: Moment
  ) {}
}
