<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="entry">
        <h2 class="jh-entity-heading" data-cy="entryDetailsHeading">
          <span v-text="$t('passwordSafeUiApp.entry.detail.title')">Entry</span> {{ entry.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('passwordSafeUiApp.entry.description')">Description</span>
          </dt>
          <dd>
            <span>{{ entry.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('passwordSafeUiApp.entry.login')">Login</span>
          </dt>
          <dd>
            <span>{{ entry.login }}</span>
          </dd>
          <dt>
            <span v-text="$t('passwordSafeUiApp.entry.passwort')">Passwort</span>
          </dt>
          <dd>
            <span>{{ entry.passwort }}</span>
          </dd>
          <dt>
            <span v-text="$t('passwordSafeUiApp.entry.passwortReplay')">Passwort Replay</span>
          </dt>
          <dd>
            <span>{{ entry.passwortReplay }}</span>
          </dd>
          <dt>
            <span v-text="$t('passwordSafeUiApp.entry.group')">Group</span>
          </dt>
          <dd>
            <div v-if="entry.group">
              <router-link :to="{ name: 'GroupView', params: { groupId: entry.group.id } }">{{ entry.group.name }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="entry.id" :to="{ name: 'EntryEdit', params: { entryId: entry.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./entry-details.component.ts"></script>
