import { Component, Vue, Inject } from 'vue-property-decorator';

import { IThementyp } from '@/shared/model/thementyp.model';
import ThementypService from './thementyp.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ThementypDetails extends Vue {
  @Inject('thementypService') private thementypService: () => ThementypService;
  @Inject('alertService') private alertService: () => AlertService;

  public thementyp: IThementyp = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.thementypId) {
        vm.retrieveThementyp(to.params.thementypId);
      }
    });
  }

  public retrieveThementyp(thementypId) {
    this.thementypService()
      .find(thementypId)
      .then(res => {
        this.thementyp = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
