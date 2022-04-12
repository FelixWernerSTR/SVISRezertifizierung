/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import BeitragDetailComponent from '@/entities/beitrag/beitrag-details.vue';
import BeitragClass from '@/entities/beitrag/beitrag-details.component';
import BeitragService from '@/entities/beitrag/beitrag.service';
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
  describe('Beitrag Management Detail Component', () => {
    let wrapper: Wrapper<BeitragClass>;
    let comp: BeitragClass;
    let beitragServiceStub: SinonStubbedInstance<BeitragService>;

    beforeEach(() => {
      beitragServiceStub = sinon.createStubInstance<BeitragService>(BeitragService);

      wrapper = shallowMount<BeitragClass>(BeitragDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { beitragService: () => beitragServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundBeitrag = { id: 123 };
        beitragServiceStub.find.resolves(foundBeitrag);

        // WHEN
        comp.retrieveBeitrag(123);
        await comp.$nextTick();

        // THEN
        expect(comp.beitrag).toBe(foundBeitrag);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundBeitrag = { id: 123 };
        beitragServiceStub.find.resolves(foundBeitrag);

        // WHEN
        comp.beforeRouteEnter({ params: { beitragId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.beitrag).toBe(foundBeitrag);
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
