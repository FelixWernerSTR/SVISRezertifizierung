import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import ThemaService from '@/entities/thema/thema.service';
import { IThema } from '@/shared/model/thema.model';

import { IBeitrag, Beitrag } from '@/shared/model/beitrag.model';
import BeitragService from './beitrag.service';
import { Archiv } from '@/shared/model/enumerations/archiv.model';

const validations: any = {
  beitrag: {
    content: {},
    attrib: {
      maxLength: maxLength(200),
    },
    titel: {
      maxLength: maxLength(70),
    },
    rechte: {
      maxLength: maxLength(30),
    },
    validFrom: {},
    validTo: {},
    publishDate: {},
    archiv: {},
  },
};

@Component({
  validations,
})
export default class BeitragUpdate extends mixins(JhiDataUtils) {
  @Inject('beitragService') private beitragService: () => BeitragService;
  @Inject('alertService') private alertService: () => AlertService;

  public beitrag: IBeitrag = new Beitrag();

  @Inject('themaService') private themaService: () => ThemaService;

  public themas: IThema[] = [];
  public archivValues: string[] = Object.keys(Archiv);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.beitragId) {
        vm.retrieveBeitrag(to.params.beitragId);
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
    if (this.beitrag.id) {
      this.beitragService()
        .update(this.beitrag)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('svisAktuellesApp.beitrag.updated', { param: param.id });
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
      this.beitragService()
        .create(this.beitrag)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('svisAktuellesApp.beitrag.created', { param: param.id });
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

  public retrieveBeitrag(beitragId): void {
    this.beitragService()
      .find(beitragId)
      .then(res => {
        this.beitrag = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.themaService()
      .retrieve()
      .then(res => {
        this.themas = res.data;
      });
  }
}
