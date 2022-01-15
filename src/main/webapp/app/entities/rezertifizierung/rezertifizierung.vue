<template>
  <div>
    <h2 id="page-heading" data-cy="RezertifizierungHeading">
      <span v-text="$t('svisRezertifizierungApp.rezertifizierung.home.title')" id="rezertifizierung-heading">Rezertifizierungs</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('svisRezertifizierungApp.rezertifizierung.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'RezertifizierungCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-rezertifizierung"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('svisRezertifizierungApp.rezertifizierung.home.createLabel')"> Create a new Rezertifizierung </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && rezertifizierungs && rezertifizierungs.length === 0">
      <span v-text="$t('svisRezertifizierungApp.rezertifizierung.home.notFound')">No rezertifizierungs found</span>
    </div>
    <div class="table-responsive" v-if="rezertifizierungs && rezertifizierungs.length > 0">
      <table class="table table-striped" aria-describedby="rezertifizierungs">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('loginName')">
              <span v-text="$t('svisRezertifizierungApp.rezertifizierung.loginName')">Login Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'loginName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('nachname')">
              <span v-text="$t('svisRezertifizierungApp.rezertifizierung.nachname')">Nachname</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nachname'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('vorname')">
              <span v-text="$t('svisRezertifizierungApp.rezertifizierung.vorname')">Vorname</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'vorname'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('vermittlerNummer')">
              <span v-text="$t('svisRezertifizierungApp.rezertifizierung.vermittlerNummer')">Vermittler Nummer</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'vermittlerNummer'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('rollenZugehoerigkeiten')">
              <span v-text="$t('svisRezertifizierungApp.rezertifizierung.rollenZugehoerigkeiten')">Rollen Zugehoerigkeiten</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'rollenZugehoerigkeiten'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dvcVertreterNummer')">
              <span v-text="$t('svisRezertifizierungApp.rezertifizierung.dvcVertreterNummer')">Dvc Vertreter Nummer</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dvcVertreterNummer'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dvcBenutzerGruppe')">
              <span v-text="$t('svisRezertifizierungApp.rezertifizierung.dvcBenutzerGruppe')">Dvc Benutzer Gruppe</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dvcBenutzerGruppe'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('icisSrGebaude')">
              <span v-text="$t('svisRezertifizierungApp.rezertifizierung.icisSrGebaude')">Icis Sr Gebaude</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'icisSrGebaude'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('icisSrHaftpflicht')">
              <span v-text="$t('svisRezertifizierungApp.rezertifizierung.icisSrHaftpflicht')">Icis Sr Haftpflicht</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'icisSrHaftpflicht'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('icisSrLeitungswasser')">
              <span v-text="$t('svisRezertifizierungApp.rezertifizierung.icisSrLeitungswasser')">Icis Sr Leitungswasser</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'icisSrLeitungswasser'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('icisSrKfzKasko')">
              <span v-text="$t('svisRezertifizierungApp.rezertifizierung.icisSrKfzKasko')">Icis Sr Kfz Kasko</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'icisSrKfzKasko'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('bestandssicht')">
              <span v-text="$t('svisRezertifizierungApp.rezertifizierung.bestandssicht')">Bestandssicht</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'bestandssicht'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('bemerkung')">
              <span v-text="$t('svisRezertifizierungApp.rezertifizierung.bemerkung')">Bemerkung</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'bemerkung'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('pruefungErfolgt')">
              <span v-text="$t('svisRezertifizierungApp.rezertifizierung.pruefungErfolgt')">Pruefung Erfolgt</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'pruefungErfolgt'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="rezertifizierung in rezertifizierungs" :key="rezertifizierung.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RezertifizierungView', params: { rezertifizierungId: rezertifizierung.id } }">{{
                rezertifizierung.id
              }}</router-link>
            </td>
            <td>{{ rezertifizierung.loginName }}</td>
            <td>{{ rezertifizierung.nachname }}</td>
            <td>{{ rezertifizierung.vorname }}</td>
            <td>{{ rezertifizierung.vermittlerNummer }}</td>
            <td>{{ rezertifizierung.rollenZugehoerigkeiten }}</td>
            <td>{{ rezertifizierung.dvcVertreterNummer }}</td>
            <td>{{ rezertifizierung.dvcBenutzerGruppe }}</td>
            <td>{{ rezertifizierung.icisSrGebaude }}</td>
            <td>{{ rezertifizierung.icisSrHaftpflicht }}</td>
            <td>{{ rezertifizierung.icisSrLeitungswasser }}</td>
            <td>{{ rezertifizierung.icisSrKfzKasko }}</td>
            <td>{{ rezertifizierung.bestandssicht }}</td>
            <td>{{ rezertifizierung.bemerkung }}</td>
            <td>{{ rezertifizierung.pruefungErfolgt }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'RezertifizierungView', params: { rezertifizierungId: rezertifizierung.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'RezertifizierungEdit', params: { rezertifizierungId: rezertifizierung.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(rezertifizierung)"
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
        ><span
          id="svisRezertifizierungApp.rezertifizierung.delete.question"
          data-cy="rezertifizierungDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p
          id="jhi-delete-rezertifizierung-heading"
          v-text="$t('svisRezertifizierungApp.rezertifizierung.delete.question', { id: removeId })"
        >
          Are you sure you want to delete this Rezertifizierung?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-rezertifizierung"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeRezertifizierung()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./rezertifizierung.component.ts"></script>
