<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="thementyp">
        <h2 class="jh-entity-heading" data-cy="thementypDetailsHeading">
          <span v-text="$t('svisInfoApp.thementyp.detail.title')">Thementyp</span> {{ thementyp.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('svisInfoApp.thementyp.name')">Name</span>
          </dt>
          <dd>
            <span>{{ thementyp.name }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="thementyp.id"
          :to="{ name: 'ThementypEdit', params: { thementypId: thementyp.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./thementyp-details.component.ts"></script>
