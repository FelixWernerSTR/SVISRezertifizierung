import { Component, Provide, Vue } from 'vue-property-decorator';

import RezertifizierungService from './rezertifizierung/rezertifizierung.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('rezertifizierungService') private rezertifizierungService = () => new RezertifizierungService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
