import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { CashManagerTestModule } from '../../../test.module';
import { ExpanseDetailComponent } from 'app/entities/expanse/expanse-detail.component';
import { Expanse } from 'app/shared/model/expanse.model';

describe('Component Tests', () => {
  describe('Expanse Management Detail Component', () => {
    let comp: ExpanseDetailComponent;
    let fixture: ComponentFixture<ExpanseDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ expanse: new Expanse(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CashManagerTestModule],
        declarations: [ExpanseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ExpanseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExpanseDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load expanse on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.expanse).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
