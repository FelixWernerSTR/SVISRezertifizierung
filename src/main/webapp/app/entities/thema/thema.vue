<template>
  <div>
    <h2 id="page-heading" data-cy="ThemaHeading">
      <span v-text="$t('svisAktuellesApp.thema.home.title')" id="thema-heading">Themas</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('svisAktuellesApp.thema.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ThemaCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-thema"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('svisAktuellesApp.thema.home.createLabel')"> Create a new Thema </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && themas && themas.length === 0">
      <span v-text="$t('svisAktuellesApp.thema.home.notFound')">No themas found</span>
    </div>
    <div class="table-responsive" v-if="themas && themas.length > 0">
      <table class="table table-striped" aria-describedby="themas">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="$t('svisAktuellesApp.thema.name')">Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('rechte')">
              <span v-text="$t('svisAktuellesApp.thema.rechte')">Rechte</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'rechte'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('displaycount')">
              <span v-text="$t('svisAktuellesApp.thema.displaycount')">Displaycount</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'displaycount'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('thementyp.name')">
              <span v-text="$t('svisAktuellesApp.thema.thementyp')">Thementyp</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'thementyp.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="thema in themas" :key="thema.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ThemaView', params: { themaId: thema.id } }">{{ thema.id }}</router-link>
            </td>
            <td>{{ thema.name }}</td>
            <td>{{ thema.rechte }}</td>
            <td>{{ thema.displaycount }}</td>
            <td>
              <div v-if="thema.thementyp">
                <router-link :to="{ name: 'ThementypView', params: { thementypId: thema.thementyp.id } }">{{
                  thema.thementyp.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ThemaView', params: { themaId: thema.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ThemaEdit', params: { themaId: thema.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(thema)"
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
        <infinite-loading
          ref="infiniteLoading"
          v-if="totalItems > itemsPerPage"
          :identifier="infiniteId"
          slot="append"
          @infinite="loadMore"
          force-use-infinite-wrapper=".el-table__body-wrapper"
          :distance="20"
        >
        </infinite-loading>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="svisAktuellesApp.thema.delete.question" data-cy="themaDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-thema-heading" v-text="$t('svisAktuellesApp.thema.delete.question', { id: removeId })">
          Are you sure you want to delete this Thema?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-thema"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeThema()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./thema.component.ts"></script>
