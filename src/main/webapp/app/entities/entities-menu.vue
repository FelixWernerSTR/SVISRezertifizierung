<template>
  <div>
    <b-dropdown-item to="/thementyp">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.thementyp')">Thementyp</span>
    </b-dropdown-item>
    <b-dropdown-item to="/thema">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.thema')">Thema</span>
    </b-dropdown-item>
    <b-dropdown-item to="/beitrag">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.beitrag')">Beitrag</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
