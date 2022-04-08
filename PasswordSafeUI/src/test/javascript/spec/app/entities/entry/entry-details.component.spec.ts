/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import EntryDetailComponent from '@/entities/entry/entry-details.vue';
import EntryClass from '@/entities/entry/entry-details.component';
import EntryService from '@/entities/entry/entry.service';
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
  describe('Entry Management Detail Component', () => {
    let wrapper: Wrapper<EntryClass>;
    let comp: EntryClass;
    let entryServiceStub: SinonStubbedInstance<EntryService>;

    beforeEach(() => {
      entryServiceStub = sinon.createStubInstance<EntryService>(EntryService);

      wrapper = shallowMount<EntryClass>(EntryDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { entryService: () => entryServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundEntry = { id: 123 };
        entryServiceStub.find.resolves(foundEntry);

        // WHEN
        comp.retrieveEntry(123);
        await comp.$nextTick();

        // THEN
        expect(comp.entry).toBe(foundEntry);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundEntry = { id: 123 };
        entryServiceStub.find.resolves(foundEntry);

        // WHEN
        comp.beforeRouteEnter({ params: { entryId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.entry).toBe(foundEntry);
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
