import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IThementyp } from '@/shared/model/thementyp.model';

import ThementypService from './thementyp.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Thementyp extends Vue {
  @Inject('thementypService') private thementypService: () => ThementypService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public thementyps: IThementyp[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllThementyps();
  }

  public clear(): void {
    this.retrieveAllThementyps();
  }

  public retrieveAllThementyps(): void {
    this.isFetching = true;
    this.thementypService()
      .retrieve()
      .then(
        res => {
          this.thementyps = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IThementyp): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeThementyp(): void {
    this.thementypService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('svisInfoApp.thementyp.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllThementyps();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
