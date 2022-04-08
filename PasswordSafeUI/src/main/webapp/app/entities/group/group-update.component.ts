import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { IGroup, Group } from '@/shared/model/group.model';
import GroupService from './group.service';

const validations: any = {
  group: {
    name: {
      required,
      maxLength: maxLength(250),
    },
    description: {
      required,
      maxLength: maxLength(250),
    },
  },
};

@Component({
  validations,
})
export default class GroupUpdate extends Vue {
  @Inject('groupService') private groupService: () => GroupService;
  @Inject('alertService') private alertService: () => AlertService;

  public group: IGroup = new Group();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.groupId) {
        vm.retrieveGroup(to.params.groupId);
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
    if (this.group.id) {
      this.groupService()
        .update(this.group)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('passwordSafeUiApp.group.updated', { param: param.id });
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
      this.groupService()
        .create(this.group)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('passwordSafeUiApp.group.created', { param: param.id });
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

  public retrieveGroup(groupId): void {
    this.groupService()
      .find(groupId)
      .then(res => {
        this.group = res;
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
