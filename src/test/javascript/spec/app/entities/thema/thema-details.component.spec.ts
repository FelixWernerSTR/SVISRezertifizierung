/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ThemaDetailComponent from '@/entities/thema/thema-details.vue';
import ThemaClass from '@/entities/thema/thema-details.component';
import ThemaService from '@/entities/thema/thema.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Thema Management Detail Component', () => {
    let wrapper: Wrapper<ThemaClass>;
    let comp: ThemaClass;
    let themaServiceStub: SinonStubbedInstance<ThemaService>;

    beforeEach(() => {
      themaServiceStub = sinon.createStubInstance<ThemaService>(ThemaService);

      wrapper = shallowMount<ThemaClass>(ThemaDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { themaService: () => themaServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundThema = { id: 123 };
        themaServiceStub.find.resolves(foundThema);

        // WHEN
        comp.retrieveThema(123);
        await comp.$nextTick();

        // THEN
        expect(comp.thema).toBe(foundThema);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundThema = { id: 123 };
        themaServiceStub.find.resolves(foundThema);

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
