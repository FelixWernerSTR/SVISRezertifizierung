<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="beitrag">
        <h2 class="jh-entity-heading" data-cy="beitragDetailsHeading">
          <span v-text="$t('svisInfoApp.beitrag.detail.title')">Beitrag</span> {{ beitrag.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('svisInfoApp.beitrag.content')">Content</span>
          </dt>
          <dd>
            <span>{{ beitrag.content }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisInfoApp.beitrag.attrib')">Attrib</span>
          </dt>
          <dd>
            <span>{{ beitrag.attrib }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisInfoApp.beitrag.titel')">Titel</span>
          </dt>
          <dd>
            <span>{{ beitrag.titel }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisInfoApp.beitrag.rechte')">Rechte</span>
          </dt>
          <dd>
            <span>{{ beitrag.rechte }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisInfoApp.beitrag.validFrom')">Valid From</span>
          </dt>
          <dd>
            <span>{{ beitrag.validFrom }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisInfoApp.beitrag.validTo')">Valid To</span>
          </dt>
          <dd>
            <span>{{ beitrag.validTo }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisInfoApp.beitrag.publishDate')">Publish Date</span>
          </dt>
          <dd>
            <span>{{ beitrag.publishDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisInfoApp.beitrag.archiv')">Archiv</span>
          </dt>
          <dd>
            <span v-text="$t('svisInfoApp.Archiv.' + beitrag.archiv)">{{ beitrag.archiv }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisInfoApp.beitrag.thema')">Thema</span>
          </dt>
          <dd>
            <div v-if="beitrag.thema">
              <router-link :to="{ name: 'ThemaView', params: { themaId: beitrag.thema.id } }">{{ beitrag.thema.name }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="beitrag.id" :to="{ name: 'BeitragEdit', params: { beitragId: beitrag.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./beitrag-details.component.ts"></script>
