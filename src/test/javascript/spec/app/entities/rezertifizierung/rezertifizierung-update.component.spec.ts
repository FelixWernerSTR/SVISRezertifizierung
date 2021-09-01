/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import RezertifizierungUpdateComponent from '@/entities/rezertifizierung/rezertifizierung-update.vue';
import RezertifizierungClass from '@/entities/rezertifizierung/rezertifizierung-update.component';
import RezertifizierungService from '@/entities/rezertifizierung/rezertifizierung.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Rezertifizierung Management Update Component', () => {
    let wrapper: Wrapper<RezertifizierungClass>;
    let comp: RezertifizierungClass;
    let rezertifizierungServiceStub: SinonStubbedInstance<RezertifizierungService>;

    beforeEach(() => {
      rezertifizierungServiceStub = sinon.createStubInstance<RezertifizierungService>(RezertifizierungService);

      wrapper = shallowMount<RezertifizierungClass>(RezertifizierungUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          rezertifizierungService: () => rezertifizierungServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.rezertifizierung = entity;
        rezertifizierungServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rezertifizierungServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.rezertifizierung = entity;
        rezertifizierungServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rezertifizierungServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRezertifizierung = { id: 123 };
        rezertifizierungServiceStub.find.resolves(foundRezertifizierung);
        rezertifizierungServiceStub.retrieve.resolves([foundRezertifizierung]);

        // WHEN
        comp.beforeRouteEnter({ params: { rezertifizierungId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.rezertifizierung).toBe(foundRezertifizierung);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
