<template>
  <div>
    <h2 id="page-heading" data-cy="BeitragHeading">
      <span v-text="$t('svisInfoApp.beitrag.home.title')" id="beitrag-heading">Beitrags</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('svisInfoApp.beitrag.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'BeitragCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-beitrag"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('svisInfoApp.beitrag.home.createLabel')"> Create a new Beitrag </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && beitrags && beitrags.length === 0">
      <span v-text="$t('svisInfoApp.beitrag.home.notFound')">No beitrags found</span>
    </div>
    <div class="table-responsive" v-if="beitrags && beitrags.length > 0">
      <table class="table table-striped" aria-describedby="beitrags">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('content')">
              <span v-text="$t('svisInfoApp.beitrag.content')">Content</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'content'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('attrib')">
              <span v-text="$t('svisInfoApp.beitrag.attrib')">Attrib</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'attrib'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('titel')">
              <span v-text="$t('svisInfoApp.beitrag.titel')">Titel</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'titel'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('rechte')">
              <span v-text="$t('svisInfoApp.beitrag.rechte')">Rechte</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'rechte'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('validFrom')">
              <span v-text="$t('svisInfoApp.beitrag.validFrom')">Valid From</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'validFrom'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('validTo')">
              <span v-text="$t('svisInfoApp.beitrag.validTo')">Valid To</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'validTo'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('publishDate')">
              <span v-text="$t('svisInfoApp.beitrag.publishDate')">Publish Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'publishDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('archiv')">
              <span v-text="$t('svisInfoApp.beitrag.archiv')">Archiv</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'archiv'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('thema.name')">
              <span v-text="$t('svisInfoApp.beitrag.thema')">Thema</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'thema.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="beitrag in beitrags" :key="beitrag.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'BeitragView', params: { beitragId: beitrag.id } }">{{ beitrag.id }}</router-link>
            </td>
            <td>{{ beitrag.content }}</td>
            <td>{{ beitrag.attrib }}</td>
            <td>{{ beitrag.titel }}</td>
            <td>{{ beitrag.rechte }}</td>
            <td>{{ beitrag.validFrom }}</td>
            <td>{{ beitrag.validTo }}</td>
            <td>{{ beitrag.publishDate }}</td>
            <td v-text="$t('svisInfoApp.Archiv.' + beitrag.archiv)">{{ beitrag.archiv }}</td>
            <td>
              <div v-if="beitrag.thema">
                <router-link :to="{ name: 'ThemaView', params: { themaId: beitrag.thema.id } }">{{ beitrag.thema.name }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'BeitragView', params: { beitragId: beitrag.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'BeitragEdit', params: { beitragId: beitrag.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(beitrag)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="svisInfoApp.beitrag.delete.question" data-cy="beitragDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-beitrag-heading" v-text="$t('svisInfoApp.beitrag.delete.question', { id: removeId })">
          Are you sure you want to delete this Beitrag?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-beitrag"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeBeitrag()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="beitrags && beitrags.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./beitrag.component.ts"></script>
