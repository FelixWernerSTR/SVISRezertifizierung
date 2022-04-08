<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="passwordSafeUiApp.entry.home.createOrEditLabel"
          data-cy="EntryCreateUpdateHeading"
          v-text="$t('passwordSafeUiApp.entry.home.createOrEditLabel')"
        >
          Create or edit a Entry
        </h2>
        <div>
          <div class="form-group" v-if="entry.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="entry.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('passwordSafeUiApp.entry.description')" for="entry-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="entry-description"
              data-cy="description"
              :class="{ valid: !$v.entry.description.$invalid, invalid: $v.entry.description.$invalid }"
              v-model="$v.entry.description.$model"
            />
            <div v-if="$v.entry.description.$anyDirty && $v.entry.description.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.entry.description.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 250 })"
              >
                This field cannot be longer than 250 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('passwordSafeUiApp.entry.login')" for="entry-login">Login</label>
            <input
              type="text"
              class="form-control"
              name="login"
              id="entry-login"
              data-cy="login"
              :class="{ valid: !$v.entry.login.$invalid, invalid: $v.entry.login.$invalid }"
              v-model="$v.entry.login.$model"
              required
            />
            <div v-if="$v.entry.login.$anyDirty && $v.entry.login.$invalid">
              <small class="form-text text-danger" v-if="!$v.entry.login.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.entry.login.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 250 })"
              >
                This field cannot be longer than 250 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('passwordSafeUiApp.entry.passwort')" for="entry-passwort">Passwort</label>
            <input
              type="text"
              class="form-control"
              name="passwort"
              id="entry-passwort"
              data-cy="passwort"
              :class="{ valid: !$v.entry.passwort.$invalid, invalid: $v.entry.passwort.$invalid }"
              v-model="$v.entry.passwort.$model"
              required
            />
            <div v-if="$v.entry.passwort.$anyDirty && $v.entry.passwort.$invalid">
              <small class="form-text text-danger" v-if="!$v.entry.passwort.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.entry.passwort.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 250 })"
              >
                This field cannot be longer than 250 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('passwordSafeUiApp.entry.passwortReplay')" for="entry-passwortReplay"
              >Passwort Replay</label
            >
            <input
              type="text"
              class="form-control"
              name="passwortReplay"
              id="entry-passwortReplay"
              data-cy="passwortReplay"
              :class="{ valid: !$v.entry.passwortReplay.$invalid, invalid: $v.entry.passwortReplay.$invalid }"
              v-model="$v.entry.passwortReplay.$model"
              required
            />
            <div v-if="$v.entry.passwortReplay.$anyDirty && $v.entry.passwortReplay.$invalid">
              <small class="form-text text-danger" v-if="!$v.entry.passwortReplay.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.entry.passwortReplay.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 250 })"
              >
                This field cannot be longer than 250 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('passwordSafeUiApp.entry.group')" for="entry-group">Group</label>
            <select class="form-control" id="entry-group" data-cy="group" name="group" v-model="entry.group">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="entry.group && groupOption.id === entry.group.id ? entry.group : groupOption"
                v-for="groupOption in groups"
                :key="groupOption.id"
              >
                {{ groupOption.name }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.entry.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./entry-update.component.ts"></script>
