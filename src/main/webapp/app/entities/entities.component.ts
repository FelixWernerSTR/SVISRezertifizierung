import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import ThementypService from './thementyp/thementyp.service';
import ThemaService from './thema/thema.service';
import BeitragService from './beitrag/beitrag.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('thementypService') private thementypService = () => new ThementypService();
  @Provide('themaService') private themaService = () => new ThemaService();
  @Provide('beitragService') private beitragService = () => new BeitragService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
