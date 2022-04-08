import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Group = () => import('@/entities/group/group.vue');
const GroupUpdate = () => import('@/entities/group/group-update.vue');
const GroupDetails = () => import('@/entities/group/group-details.vue');

const Entry = () => import('@/entities/entry/entry.vue');
const EntryUpdate = () => import('@/entities/entry/entry-update.vue');
const EntryDetails = () => import('@/entities/entry/entry-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'group',
      name: 'Group',
      component: Group,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'group/new',
      name: 'GroupCreate',
      component: GroupUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'group/:groupId/edit',
      name: 'GroupEdit',
      component: GroupUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'group/:groupId/view',
      name: 'GroupView',
      component: GroupDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'entry',
      name: 'Entry',
      component: Entry,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'entry/new',
      name: 'EntryCreate',
      component: EntryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'entry/:entryId/edit',
      name: 'EntryEdit',
      component: EntryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'entry/:entryId/view',
      name: 'EntryView',
      component: EntryDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
