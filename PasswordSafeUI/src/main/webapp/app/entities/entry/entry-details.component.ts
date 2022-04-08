import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEntry } from '@/shared/model/entry.model';
import EntryService from './entry.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class EntryDetails extends Vue {
  @Inject('entryService') private entryService: () => EntryService;
  @Inject('alertService') private alertService: () => AlertService;

  public entry: IEntry = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.entryId) {
        vm.retrieveEntry(to.params.entryId);
      }
    });
  }

  public retrieveEntry(entryId) {
    this.entryService()
      .find(entryId)
      .then(res => {
        this.entry = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
