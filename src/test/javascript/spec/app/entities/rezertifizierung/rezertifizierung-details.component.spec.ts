/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import RezertifizierungDetailComponent from '@/entities/rezertifizierung/rezertifizierung-details.vue';
import RezertifizierungClass from '@/entities/rezertifizierung/rezertifizierung-details.component';
import RezertifizierungService from '@/entities/rezertifizierung/rezertifizierung.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Rezertifizierung Management Detail Component', () => {
    let wrapper: Wrapper<RezertifizierungClass>;
    let comp: RezertifizierungClass;
    let rezertifizierungServiceStub: SinonStubbedInstance<RezertifizierungService>;

    beforeEach(() => {
      rezertifizierungServiceStub = sinon.createStubInstance<RezertifizierungService>(RezertifizierungService);

      wrapper = shallowMount<RezertifizierungClass>(RezertifizierungDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { rezertifizierungService: () => rezertifizierungServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRezertifizierung = { id: 123 };
        rezertifizierungServiceStub.find.resolves(foundRezertifizierung);

        // WHEN
        comp.retrieveRezertifizierung(123);
        await comp.$nextTick();

        // THEN
        expect(comp.rezertifizierung).toBe(foundRezertifizierung);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRezertifizierung = { id: 123 };
        rezertifizierungServiceStub.find.resolves(foundRezertifizierung);

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
