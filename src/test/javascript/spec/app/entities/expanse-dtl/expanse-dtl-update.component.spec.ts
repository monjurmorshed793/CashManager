import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CashManagerTestModule } from '../../../test.module';
import { ExpanseDtlUpdateComponent } from 'app/entities/expanse-dtl/expanse-dtl-update.component';
import { ExpanseDtlService } from 'app/entities/expanse-dtl/expanse-dtl.service';
import { ExpanseDtl } from 'app/shared/model/expanse-dtl.model';

describe('Component Tests', () => {
  describe('ExpanseDtl Management Update Component', () => {
    let comp: ExpanseDtlUpdateComponent;
    let fixture: ComponentFixture<ExpanseDtlUpdateComponent>;
    let service: ExpanseDtlService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CashManagerTestModule],
        declarations: [ExpanseDtlUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ExpanseDtlUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExpanseDtlUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExpanseDtlService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ExpanseDtl(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ExpanseDtl();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
