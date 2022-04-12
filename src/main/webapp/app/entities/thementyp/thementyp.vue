<template>
  <div>
    <h2 id="page-heading" data-cy="ThementypHeading">
      <span v-text="$t('svisAktuellesApp.thementyp.home.title')" id="thementyp-heading">Thementyps</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('svisAktuellesApp.thementyp.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ThementypCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-thementyp"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('svisAktuellesApp.thementyp.home.createLabel')"> Create a new Thementyp </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && thementyps && thementyps.length === 0">
      <span v-text="$t('svisAktuellesApp.thementyp.home.notFound')">No thementyps found</span>
    </div>
    <div class="table-responsive" v-if="thementyps && thementyps.length > 0">
      <table class="table table-striped" aria-describedby="thementyps">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('svisAktuellesApp.thementyp.name')">Name</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="thementyp in thementyps" :key="thementyp.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ThementypView', params: { thementypId: thementyp.id } }">{{ thementyp.id }}</router-link>
            </td>
            <td>{{ thementyp.name }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ThementypView', params: { thementypId: thementyp.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ThementypEdit', params: { thementypId: thementyp.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(thementyp)"
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
        ><span id="svisAktuellesApp.thementyp.delete.question" data-cy="thementypDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-thementyp-heading" v-text="$t('svisAktuellesApp.thementyp.delete.question', { id: removeId })">
          Are you sure you want to delete this Thementyp?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-thementyp"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeThementyp()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./thementyp.component.ts"></script>
