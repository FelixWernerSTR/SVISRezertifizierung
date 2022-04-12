import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { IThementyp, Thementyp } from '@/shared/model/thementyp.model';
import ThementypService from './thementyp.service';

const validations: any = {
  thementyp: {
    name: {
      required,
      maxLength: maxLength(50),
    },
  },
};

@Component({
  validations,
})
export default class ThementypUpdate extends Vue {
  @Inject('thementypService') private thementypService: () => ThementypService;
  @Inject('alertService') private alertService: () => AlertService;

  public thementyp: IThementyp = new Thementyp();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.thementypId) {
        vm.retrieveThementyp(to.params.thementypId);
      }
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
    if (this.thementyp.id) {
      this.thementypService()
        .update(this.thementyp)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('svisAktuellesApp.thementyp.updated', { param: param.id });
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
      this.thementypService()
        .create(this.thementyp)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('svisAktuellesApp.thementyp.created', { param: param.id });
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

  public retrieveThementyp(thementypId): void {
    this.thementypService()
      .find(thementypId)
      .then(res => {
        this.thementyp = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
