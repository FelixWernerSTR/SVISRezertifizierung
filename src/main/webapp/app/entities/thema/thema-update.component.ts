import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, maxLength, numeric, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import ThementypService from '@/entities/thementyp/thementyp.service';
import { IThementyp } from '@/shared/model/thementyp.model';

import { IThema, Thema } from '@/shared/model/thema.model';
import ThemaService from './thema.service';

const validations: any = {
  thema: {
    name: {
      required,
      maxLength: maxLength(50),
    },
    rechte: {
      maxLength: maxLength(30),
    },
    displaycount: {
      numeric,
      min: minValue(0),
      max: maxValue(999),
    },
  },
};

@Component({
  validations,
})
export default class ThemaUpdate extends Vue {
  @Inject('themaService') private themaService: () => ThemaService;
  @Inject('alertService') private alertService: () => AlertService;

  public thema: IThema = new Thema();

  @Inject('thementypService') private thementypService: () => ThementypService;

  public thementyps: IThementyp[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.themaId) {
        vm.retrieveThema(to.params.themaId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.thema.id) {
      this.themaService()
        .update(this.thema)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('svisInfoApp.thema.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.themaService()
        .create(this.thema)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('svisInfoApp.thema.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveThema(themaId): void {
    this.themaService()
      .find(themaId)
      .then(res => {
        this.thema = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.thementypService()
      .retrieve()
      .then(res => {
        this.thementyps = res.data;
      });
  }
}
