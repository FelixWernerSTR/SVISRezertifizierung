<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="svisAktuellesApp.beitrag.home.createOrEditLabel"
          data-cy="BeitragCreateUpdateHeading"
          v-text="$t('svisAktuellesApp.beitrag.home.createOrEditLabel')"
        >
          Create or edit a Beitrag
        </h2>
        <div>
          <div class="form-group" v-if="beitrag.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="beitrag.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('svisAktuellesApp.beitrag.content')" for="beitrag-content">Content</label>
            <textarea
              class="form-control"
              name="content"
              id="beitrag-content"
              data-cy="content"
              :class="{ valid: !$v.beitrag.content.$invalid, invalid: $v.beitrag.content.$invalid }"
              v-model="$v.beitrag.content.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('svisAktuellesApp.beitrag.attrib')" for="beitrag-attrib">Attrib</label>
            <input
              type="text"
              class="form-control"
              name="attrib"
              id="beitrag-attrib"
              data-cy="attrib"
              :class="{ valid: !$v.beitrag.attrib.$invalid, invalid: $v.beitrag.attrib.$invalid }"
              v-model="$v.beitrag.attrib.$model"
            />
            <div v-if="$v.beitrag.attrib.$anyDirty && $v.beitrag.attrib.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.beitrag.attrib.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 200 })"
              >
                This field cannot be longer than 200 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('svisAktuellesApp.beitrag.titel')" for="beitrag-titel">Titel</label>
            <input
              type="text"
              class="form-control"
              name="titel"
              id="beitrag-titel"
              data-cy="titel"
              :class="{ valid: !$v.beitrag.titel.$invalid, invalid: $v.beitrag.titel.$invalid }"
              v-model="$v.beitrag.titel.$model"
            />
            <div v-if="$v.beitrag.titel.$anyDirty && $v.beitrag.titel.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.beitrag.titel.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 70 })"
              >
                This field cannot be longer than 70 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('svisAktuellesApp.beitrag.rechte')" for="beitrag-rechte">Rechte</label>
            <input
              type="text"
              class="form-control"
              name="rechte"
              id="beitrag-rechte"
              data-cy="rechte"
              :class="{ valid: !$v.beitrag.rechte.$invalid, invalid: $v.beitrag.rechte.$invalid }"
              v-model="$v.beitrag.rechte.$model"
            />
            <div v-if="$v.beitrag.rechte.$anyDirty && $v.beitrag.rechte.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.beitrag.rechte.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 30 })"
              >
                This field cannot be longer than 30 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('svisAktuellesApp.beitrag.validFrom')" for="beitrag-validFrom">Valid From</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="beitrag-validFrom"
                  v-model="$v.beitrag.validFrom.$model"
                  name="validFrom"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="beitrag-validFrom"
                data-cy="validFrom"
                type="text"
                class="form-control"
                name="validFrom"
                :class="{ valid: !$v.beitrag.validFrom.$invalid, invalid: $v.beitrag.validFrom.$invalid }"
                v-model="$v.beitrag.validFrom.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('svisAktuellesApp.beitrag.validTo')" for="beitrag-validTo">Valid To</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="beitrag-validTo"
                  v-model="$v.beitrag.validTo.$model"
                  name="validTo"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="beitrag-validTo"
                data-cy="validTo"
                type="text"
                class="form-control"
                name="validTo"
                :class="{ valid: !$v.beitrag.validTo.$invalid, invalid: $v.beitrag.validTo.$invalid }"
                v-model="$v.beitrag.validTo.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('svisAktuellesApp.beitrag.publishDate')" for="beitrag-publishDate"
              >Publish Date</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="beitrag-publishDate"
                  v-model="$v.beitrag.publishDate.$model"
                  name="publishDate"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="beitrag-publishDate"
                data-cy="publishDate"
                type="text"
                class="form-control"
                name="publishDate"
                :class="{ valid: !$v.beitrag.publishDate.$invalid, invalid: $v.beitrag.publishDate.$invalid }"
                v-model="$v.beitrag.publishDate.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('svisAktuellesApp.beitrag.archiv')" for="beitrag-archiv">Archiv</label>
            <select
              class="form-control"
              name="archiv"
              :class="{ valid: !$v.beitrag.archiv.$invalid, invalid: $v.beitrag.archiv.$invalid }"
              v-model="$v.beitrag.archiv.$model"
              id="beitrag-archiv"
              data-cy="archiv"
            >
              <option
                v-for="archiv in archivValues"
                :key="archiv"
                v-bind:value="archiv"
                v-bind:label="$t('svisAktuellesApp.Archiv.' + archiv)"
              >
                {{ archiv }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('svisAktuellesApp.beitrag.thema')" for="beitrag-thema">Thema</label>
            <select class="form-control" id="beitrag-thema" data-cy="thema" name="thema" v-model="beitrag.thema">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="beitrag.thema && themaOption.id === beitrag.thema.id ? beitrag.thema : themaOption"
                v-for="themaOption in themas"
                :key="themaOption.id"
              >
                {{ themaOption.name }}
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
            :disabled="$v.beitrag.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./beitrag-update.component.ts"></script>
