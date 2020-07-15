import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CashManagerTestModule } from '../../../test.module';
import { ExpanseDtlDetailComponent } from 'app/entities/expanse-dtl/expanse-dtl-detail.component';
import { ExpanseDtl } from 'app/shared/model/expanse-dtl.model';

describe('Component Tests', () => {
  describe('ExpanseDtl Management Detail Component', () => {
    let comp: ExpanseDtlDetailComponent;
    let fixture: ComponentFixture<ExpanseDtlDetailComponent>;
    const route = ({ data: of({ expanseDtl: new ExpanseDtl(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CashManagerTestModule],
        declarations: [ExpanseDtlDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ExpanseDtlDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExpanseDtlDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load expanseDtl on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.expanseDtl).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
