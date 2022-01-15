import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Rezertifizierung = () => import('@/entities/rezertifizierung/rezertifizierung.vue');
const RezertifizierungUpdate = () => import('@/entities/rezertifizierung/rezertifizierung-update.vue');
const RezertifizierungDetails = () => import('@/entities/rezertifizierung/rezertifizierung-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'rezertifizierung',
      name: 'Rezertifizierung',
      component: Rezertifizierung,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rezertifizierung/new',
      name: 'RezertifizierungCreate',
      component: RezertifizierungUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rezertifizierung/:rezertifizierungId/edit',
      name: 'RezertifizierungEdit',
      component: RezertifizierungUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rezertifizierung/:rezertifizierungId/view',
      name: 'RezertifizierungView',
      component: RezertifizierungDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
