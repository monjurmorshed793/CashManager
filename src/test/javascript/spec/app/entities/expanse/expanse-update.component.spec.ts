import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CashManagerTestModule } from '../../../test.module';
import { ExpanseUpdateComponent } from 'app/entities/expanse/expanse-update.component';
import { ExpanseService } from 'app/entities/expanse/expanse.service';
import { Expanse } from 'app/shared/model/expanse.model';

describe('Component Tests', () => {
  describe('Expanse Management Update Component', () => {
    let comp: ExpanseUpdateComponent;
    let fixture: ComponentFixture<ExpanseUpdateComponent>;
    let service: ExpanseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CashManagerTestModule],
        declarations: [ExpanseUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ExpanseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExpanseUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExpanseService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Expanse(123);
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
        const entity = new Expanse();
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
