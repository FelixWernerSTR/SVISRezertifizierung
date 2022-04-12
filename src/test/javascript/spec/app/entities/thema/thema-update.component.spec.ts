/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ThemaUpdateComponent from '@/entities/thema/thema-update.vue';
import ThemaClass from '@/entities/thema/thema-update.component';
import ThemaService from '@/entities/thema/thema.service';

import ThementypService from '@/entities/thementyp/thementyp.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Thema Management Update Component', () => {
    let wrapper: Wrapper<ThemaClass>;
    let comp: ThemaClass;
    let themaServiceStub: SinonStubbedInstance<ThemaService>;

    beforeEach(() => {
      themaServiceStub = sinon.createStubInstance<ThemaService>(ThemaService);

      wrapper = shallowMount<ThemaClass>(ThemaUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          themaService: () => themaServiceStub,
          alertService: () => new AlertService(),

          thementypService: () =>
            sinon.createStubInstance<ThementypService>(ThementypService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.thema = entity;
        themaServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(themaServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.thema = entity;
        themaServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(themaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundThema = { id: 123 };
        themaServiceStub.find.resolves(foundThema);
        themaServiceStub.retrieve.resolves([foundThema]);

        // WHEN
        comp.beforeRouteEnter({ params: { themaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.thema).toBe(foundThema);
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
