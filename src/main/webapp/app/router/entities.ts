import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Rezertifizierung = () => import('@/entities/rezertifizierung/rezertifizierung.vue');
// prettier-ignore
const RezertifizierungUpdate = () => import('@/entities/rezertifizierung/rezertifizierung-update.vue');
// prettier-ignore
const RezertifizierungDetails = () => import('@/entities/rezertifizierung/rezertifizierung-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/rezertifizierung',
    name: 'Rezertifizierung',
    component: Rezertifizierung,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/rezertifizierung/new',
    name: 'RezertifizierungCreate',
    component: RezertifizierungUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/rezertifizierung/:rezertifizierungId/edit',
    name: 'RezertifizierungEdit',
    component: RezertifizierungUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/rezertifizierung/:rezertifizierungId/view',
    name: 'RezertifizierungView',
    component: RezertifizierungDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
