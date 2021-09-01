<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="rezertifizierung">
        <h2 class="jh-entity-heading" data-cy="rezertifizierungDetailsHeading">
          <span v-text="$t('svisRezertifizierungApp.rezertifizierung.detail.title')">Rezertifizierung</span> {{ rezertifizierung.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('svisRezertifizierungApp.rezertifizierung.loginName')">Login Name</span>
          </dt>
          <dd>
            <span>{{ rezertifizierung.loginName }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisRezertifizierungApp.rezertifizierung.nachname')">Nachname</span>
          </dt>
          <dd>
            <span>{{ rezertifizierung.nachname }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisRezertifizierungApp.rezertifizierung.vorname')">Vorname</span>
          </dt>
          <dd>
            <span>{{ rezertifizierung.vorname }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisRezertifizierungApp.rezertifizierung.vermittlerNummer')">Vermittler Nummer</span>
          </dt>
          <dd>
            <span>{{ rezertifizierung.vermittlerNummer }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisRezertifizierungApp.rezertifizierung.rollenZugehoerigkeiten')">Rollen Zugehoerigkeiten</span>
          </dt>
          <dd>
            <span>{{ rezertifizierung.rollenZugehoerigkeiten }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisRezertifizierungApp.rezertifizierung.dvcVertreterNummer')">Dvc Vertreter Nummer</span>
          </dt>
          <dd>
            <span>{{ rezertifizierung.dvcVertreterNummer }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisRezertifizierungApp.rezertifizierung.dvcBenutzerGruppe')">Dvc Benutzer Gruppe</span>
          </dt>
          <dd>
            <span>{{ rezertifizierung.dvcBenutzerGruppe }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisRezertifizierungApp.rezertifizierung.icisSrGebaude')">Icis Sr Gebaude</span>
          </dt>
          <dd>
            <span>{{ rezertifizierung.icisSrGebaude }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisRezertifizierungApp.rezertifizierung.icisSrHaftpflicht')">Icis Sr Haftpflicht</span>
          </dt>
          <dd>
            <span>{{ rezertifizierung.icisSrHaftpflicht }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisRezertifizierungApp.rezertifizierung.icisSrLeitungswasser')">Icis Sr Leitungswasser</span>
          </dt>
          <dd>
            <span>{{ rezertifizierung.icisSrLeitungswasser }}</span>
          </dd>
          <dt>
            <span v-text="$t('svisRezertifizierungApp.rezertifizierung.icisSrKfzKasko')">Icis Sr Kfz Kasko</span>
          </dt>
          <dd>
            <span>{{ rezertifizierung.icisSrKfzKasko }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="rezertifizierung.id"
          :to="{ name: 'RezertifizierungEdit', params: { rezertifizierungId: rezertifizierung.id } }"
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

<script lang="ts" src="./rezertifizierung-details.component.ts"></script>
