import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { DepositExtendedService } from './deposit-extended.service';
import { DepositUpdateComponent } from '../../entities/deposit/deposit-update.component';

@Component({
  selector: 'jhi-deposit-update',
  templateUrl: './deposit-extended-update.component.html',
})
export class DepositExtendedUpdateComponent extends DepositUpdateComponent implements OnInit {
  constructor(protected depositService: DepositExtendedService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {
    super(depositService, activatedRoute, fb);
  }
}
