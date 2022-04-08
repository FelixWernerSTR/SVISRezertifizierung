import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import GroupService from './group/group.service';
import EntryService from './entry/entry.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('groupService') private groupService = () => new GroupService();
  @Provide('entryService') private entryService = () => new EntryService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
