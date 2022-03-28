import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IRezertifizierung } from '@/shared/model/rezertifizierung.model';
import RezertifizierungService from './rezertifizierung.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class RezertifizierungDetails extends mixins(JhiDataUtils) {
  @Inject('rezertifizierungService') private rezertifizierungService: () => RezertifizierungService;
  @Inject('alertService') private alertService: () => AlertService;

  public rezertifizierung: IRezertifizierung = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.rezertifizierungId) {
        vm.retrieveRezertifizierung(to.params.rezertifizierungId);
      }
    });
  }

  public retrieveRezertifizierung(rezertifizierungId) {
    this.rezertifizierungService()
      .find(rezertifizierungId)
      .then(res => {
        this.rezertifizierung = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
