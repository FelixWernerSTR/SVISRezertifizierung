/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ThementypDetailComponent from '@/entities/thementyp/thementyp-details.vue';
import ThementypClass from '@/entities/thementyp/thementyp-details.component';
import ThementypService from '@/entities/thementyp/thementyp.service';
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
  describe('Thementyp Management Detail Component', () => {
    let wrapper: Wrapper<ThementypClass>;
    let comp: ThementypClass;
    let thementypServiceStub: SinonStubbedInstance<ThementypService>;

    beforeEach(() => {
      thementypServiceStub = sinon.createStubInstance<ThementypService>(ThementypService);

      wrapper = shallowMount<ThementypClass>(ThementypDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { thementypService: () => thementypServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundThementyp = { id: 123 };
        thementypServiceStub.find.resolves(foundThementyp);

        // WHEN
        comp.retrieveThementyp(123);
        await comp.$nextTick();

        // THEN
        expect(comp.thementyp).toBe(foundThementyp);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundThementyp = { id: 123 };
        thementypServiceStub.find.resolves(foundThementyp);

        // WHEN
        comp.beforeRouteEnter({ params: { thementypId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.thementyp).toBe(foundThementyp);
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
