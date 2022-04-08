<template>
  <div>
    <h2 id="page-heading" data-cy="EntryHeading">
      <span v-text="$t('passwordSafeUiApp.entry.home.title')" id="entry-heading">Entries</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('passwordSafeUiApp.entry.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'EntryCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-entry"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('passwordSafeUiApp.entry.home.createLabel')"> Create a new Entry </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && entries && entries.length === 0">
      <span v-text="$t('passwordSafeUiApp.entry.home.notFound')">No entries found</span>
    </div>
    <div class="table-responsive" v-if="entries && entries.length > 0">
      <table class="table table-striped" aria-describedby="entries">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="$t('passwordSafeUiApp.entry.description')">Description</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('login')">
              <span v-text="$t('passwordSafeUiApp.entry.login')">Login</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'login'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('passwort')">
              <span v-text="$t('passwordSafeUiApp.entry.passwort')">Passwort</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'passwort'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('passwortReplay')">
              <span v-text="$t('passwordSafeUiApp.entry.passwortReplay')">Passwort Replay</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'passwortReplay'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('group.name')">
              <span v-text="$t('passwordSafeUiApp.entry.group')">Group</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'group.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="entry in entries" :key="entry.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'EntryView', params: { entryId: entry.id } }">{{ entry.id }}</router-link>
            </td>
            <td>{{ entry.description }}</td>
            <td>{{ entry.login }}</td>
            <td>{{ entry.passwort }}</td>
            <td>{{ entry.passwortReplay }}</td>
            <td>
              <div v-if="entry.group">
                <router-link :to="{ name: 'GroupView', params: { groupId: entry.group.id } }">{{ entry.group.name }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'EntryView', params: { entryId: entry.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'EntryEdit', params: { entryId: entry.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(entry)"
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
        ><span id="passwordSafeUiApp.entry.delete.question" data-cy="entryDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-entry-heading" v-text="$t('passwordSafeUiApp.entry.delete.question', { id: removeId })">
          Are you sure you want to delete this Entry?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-entry"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeEntry()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="entries && entries.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./entry.component.ts"></script>
