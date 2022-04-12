<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="svisInfoApp.thema.home.createOrEditLabel"
          data-cy="ThemaCreateUpdateHeading"
          v-text="$t('svisInfoApp.thema.home.createOrEditLabel')"
        >
          Create or edit a Thema
        </h2>
        <div>
          <div class="form-group" v-if="thema.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="thema.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('svisInfoApp.thema.name')" for="thema-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="thema-name"
              data-cy="name"
              :class="{ valid: !$v.thema.name.$invalid, invalid: $v.thema.name.$invalid }"
              v-model="$v.thema.name.$model"
              required
            />
            <div v-if="$v.thema.name.$anyDirty && $v.thema.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.thema.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.thema.name.maxLength" v-text="$t('entity.validation.maxlength', { max: 50 })">
                This field cannot be longer than 50 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('svisInfoApp.thema.rechte')" for="thema-rechte">Rechte</label>
            <input
              type="text"
              class="form-control"
              name="rechte"
              id="thema-rechte"
              data-cy="rechte"
              :class="{ valid: !$v.thema.rechte.$invalid, invalid: $v.thema.rechte.$invalid }"
              v-model="$v.thema.rechte.$model"
            />
            <div v-if="$v.thema.rechte.$anyDirty && $v.thema.rechte.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.thema.rechte.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 30 })"
              >
                This field cannot be longer than 30 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('svisInfoApp.thema.displaycount')" for="thema-displaycount">Displaycount</label>
            <input
              type="number"
              class="form-control"
              name="displaycount"
              id="thema-displaycount"
              data-cy="displaycount"
              :class="{ valid: !$v.thema.displaycount.$invalid, invalid: $v.thema.displaycount.$invalid }"
              v-model.number="$v.thema.displaycount.$model"
            />
            <div v-if="$v.thema.displaycount.$anyDirty && $v.thema.displaycount.$invalid">
              <small class="form-text text-danger" v-if="!$v.thema.displaycount.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.thema.displaycount.max" v-text="$t('entity.validation.max', { max: 999 })">
                This field cannot be longer than 999 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.thema.displaycount.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('svisInfoApp.thema.thementyp')" for="thema-thementyp">Thementyp</label>
            <select class="form-control" id="thema-thementyp" data-cy="thementyp" name="thementyp" v-model="thema.thementyp">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="thema.thementyp && thementypOption.id === thema.thementyp.id ? thema.thementyp : thementypOption"
                v-for="thementypOption in thementyps"
                :key="thementypOption.id"
              >
                {{ thementypOption.name }}
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
            :disabled="$v.thema.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./thema-update.component.ts"></script>
