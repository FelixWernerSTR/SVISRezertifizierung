/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ThementypComponent from '@/entities/thementyp/thementyp.vue';
import ThementypClass from '@/entities/thementyp/thementyp.component';
import ThementypService from '@/entities/thementyp/thementyp.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Thementyp Management Component', () => {
    let wrapper: Wrapper<ThementypClass>;
    let comp: ThementypClass;
    let thementypServiceStub: SinonStubbedInstance<ThementypService>;

    beforeEach(() => {
      thementypServiceStub = sinon.createStubInstance<ThementypService>(ThementypService);
      thementypServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ThementypClass>(ThementypComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          thementypService: () => thementypServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      thementypServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllThementyps();
      await comp.$nextTick();

      // THEN
      expect(thementypServiceStub.retrieve.called).toBeTruthy();
      expect(comp.thementyps[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      thementypServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(thementypServiceStub.retrieve.callCount).toEqual(1);

      comp.removeThementyp();
      await comp.$nextTick();

      // THEN
      expect(thementypServiceStub.delete.called).toBeTruthy();
      expect(thementypServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
