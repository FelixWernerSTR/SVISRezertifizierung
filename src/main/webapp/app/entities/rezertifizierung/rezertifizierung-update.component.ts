import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { maxLength, numeric, required } from 'vuelidate/lib/validators';

import { IRezertifizierung, Rezertifizierung } from '@/shared/model/rezertifizierung.model';
import RezertifizierungService from './rezertifizierung.service';

const validations: any = {
  rezertifizierung: {
    loginName: {
      maxLength: maxLength(100),
    },
    nachname: {
      maxLength: maxLength(100),
    },
    vorname: {
      maxLength: maxLength(100),
    },
    vermittlerNummer: {
      required,
      numeric,
    },
    rollenZugehoerigkeiten: {},
    dvcVertreterNummer: {
      maxLength: maxLength(100),
    },
    dvcBenutzerGruppe: {
      maxLength: maxLength(100),
    },
    icisSrGebaude: {
      maxLength: maxLength(100),
    },
    icisSrHaftpflicht: {
      maxLength: maxLength(100),
    },
    icisSrLeitungswasser: {
      maxLength: maxLength(100),
    },
    icisSrKfzKasko: {
      maxLength: maxLength(100),
    },
    bestandssicht: {},
    bemerkung: {},
    pruefungErfolgt: {},
  },
};

@Component({
  validations,
})
export default class RezertifizierungUpdate extends mixins(JhiDataUtils) {
  @Inject('rezertifizierungService') private rezertifizierungService: () => RezertifizierungService;
  public rezertifizierung: IRezertifizierung = new Rezertifizierung();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.rezertifizierungId) {
        vm.retrieveRezertifizierung(to.params.rezertifizierungId);
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
    if (this.rezertifizierung.id) {
      this.rezertifizierungService()
        .update(this.rezertifizierung)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('svisRezertifizierungApp.rezertifizierung.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.rezertifizierungService()
        .create(this.rezertifizierung)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('svisRezertifizierungApp.rezertifizierung.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveRezertifizierung(rezertifizierungId): void {
    this.rezertifizierungService()
      .find(rezertifizierungId)
      .then(res => {
        this.rezertifizierung = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
