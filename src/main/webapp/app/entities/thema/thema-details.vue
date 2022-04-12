<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="thema">
        <h2 class="jh-entity-heading" data-cy="themaDetailsHeading">
          <span v-text="$t('svisAktuellesApp.thema.detail.title')">Thema</span> {{ thema.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('svisAktuellesApp.thema.name')">Name</span>
          </dt>
          <dd>
            <span>{{ thema.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisAktuellesApp.thema.rechte')">Rechte</span>
          </dt>
          <dd>
            <span>{{ thema.rechte }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisAktuellesApp.thema.displaycount')">Displaycount</span>
          </dt>
          <dd>
            <span>{{ thema.displaycount }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisAktuellesApp.thema.thementyp')">Thementyp</span>
          </dt>
          <dd>
            <div v-if="thema.thementyp">
              <router-link :to="{ name: 'ThementypView', params: { thementypId: thema.thementyp.id } }">{{
                thema.thementyp.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="thema.id" :to="{ name: 'ThemaEdit', params: { themaId: thema.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./thema-details.component.ts"></script>
