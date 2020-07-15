import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ExpanseService } from 'app/entities/expanse/expanse.service';
import { IExpanse, Expanse } from 'app/shared/model/expanse.model';
import { MonthType } from 'app/shared/model/enumerations/month-type.model';

describe('Service Tests', () => {
  describe('Expanse Service', () => {
    let injector: TestBed;
    let service: ExpanseService;
    let httpMock: HttpTestingController;
    let elemDefault: IExpanse;
    let expectedResult: IExpanse | IExpanse[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ExpanseService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Expanse(0, 'AAAAAAA', 0, currentDate, MonthType.JANUARY, 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            voucherDate: currentDate.format(DATE_FORMAT),
            createdOn: currentDate.format(DATE_TIME_FORMAT),
            modifiedOn: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Expanse', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            voucherDate: currentDate.format(DATE_FORMAT),
            createdOn: currentDate.format(DATE_TIME_FORMAT),
            modifiedOn: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            voucherDate: currentDate,
            createdOn: currentDate,
            modifiedOn: currentDate,
          },
          returnedFromService
        );

        service.create(new Expanse()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Expanse', () => {
        const returnedFromService = Object.assign(
          {
            loginId: 'BBBBBB',
            voucherNo: 1,
            voucherDate: currentDate.format(DATE_FORMAT),
            month: 'BBBBBB',
            notes: 'BBBBBB',
            createdBy: 'BBBBBB',
            createdOn: currentDate.format(DATE_TIME_FORMAT),
            modifiedBy: 'BBBBBB',
            modifiedOn: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            voucherDate: currentDate,
            createdOn: currentDate,
            modifiedOn: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Expanse', () => {
        const returnedFromService = Object.assign(
          {
            loginId: 'BBBBBB',
            voucherNo: 1,
            voucherDate: currentDate.format(DATE_FORMAT),
            month: 'BBBBBB',
            notes: 'BBBBBB',
            createdBy: 'BBBBBB',
            createdOn: currentDate.format(DATE_TIME_FORMAT),
            modifiedBy: 'BBBBBB',
            modifiedOn: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            voucherDate: currentDate,
            createdOn: currentDate,
            modifiedOn: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Expanse', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
