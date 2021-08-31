<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="svisRezertifizierungApp.rezertifizierung.home.createOrEditLabel"
          data-cy="RezertifizierungCreateUpdateHeading"
          v-text="$t('svisRezertifizierungApp.rezertifizierung.home.createOrEditLabel')"
        >
          Create or edit a Rezertifizierung
        </h2>
        <div>
          <div class="form-group" v-if="rezertifizierung.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="rezertifizierung.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('svisRezertifizierungApp.rezertifizierung.loginName')"
              for="rezertifizierung-loginName"
              >Login Name</label
            >
            <input
              type="text"
              class="form-control"
              name="loginName"
              id="rezertifizierung-loginName"
              data-cy="loginName"
              :class="{ valid: !$v.rezertifizierung.loginName.$invalid, invalid: $v.rezertifizierung.loginName.$invalid }"
              v-model="$v.rezertifizierung.loginName.$model"
            />
            <div v-if="$v.rezertifizierung.loginName.$anyDirty && $v.rezertifizierung.loginName.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.rezertifizierung.loginName.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('svisRezertifizierungApp.rezertifizierung.nachname')"
              for="rezertifizierung-nachname"
              >Nachname</label
            >
            <input
              type="text"
              class="form-control"
              name="nachname"
              id="rezertifizierung-nachname"
              data-cy="nachname"
              :class="{ valid: !$v.rezertifizierung.nachname.$invalid, invalid: $v.rezertifizierung.nachname.$invalid }"
              v-model="$v.rezertifizierung.nachname.$model"
            />
            <div v-if="$v.rezertifizierung.nachname.$anyDirty && $v.rezertifizierung.nachname.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.rezertifizierung.nachname.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('svisRezertifizierungApp.rezertifizierung.vorname')" for="rezertifizierung-vorname"
              >Vorname</label
            >
            <input
              type="text"
              class="form-control"
              name="vorname"
              id="rezertifizierung-vorname"
              data-cy="vorname"
              :class="{ valid: !$v.rezertifizierung.vorname.$invalid, invalid: $v.rezertifizierung.vorname.$invalid }"
              v-model="$v.rezertifizierung.vorname.$model"
            />
            <div v-if="$v.rezertifizierung.vorname.$anyDirty && $v.rezertifizierung.vorname.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.rezertifizierung.vorname.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('svisRezertifizierungApp.rezertifizierung.vermittlerNummer')"
              for="rezertifizierung-vermittlerNummer"
              >Vermittler Nummer</label
            >
            <input
              type="number"
              class="form-control"
              name="vermittlerNummer"
              id="rezertifizierung-vermittlerNummer"
              data-cy="vermittlerNummer"
              :class="{ valid: !$v.rezertifizierung.vermittlerNummer.$invalid, invalid: $v.rezertifizierung.vermittlerNummer.$invalid }"
              v-model.number="$v.rezertifizierung.vermittlerNummer.$model"
              required
            />
            <div v-if="$v.rezertifizierung.vermittlerNummer.$anyDirty && $v.rezertifizierung.vermittlerNummer.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.rezertifizierung.vermittlerNummer.required"
                v-text="$t('entity.validation.required')"
              >
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.rezertifizierung.vermittlerNummer.numeric"
                v-text="$t('entity.validation.number')"
              >
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('svisRezertifizierungApp.rezertifizierung.rollenZugehoerigkeiten')"
              for="rezertifizierung-rollenZugehoerigkeiten"
              >Rollen Zugehoerigkeiten</label
            >
            <textarea
              class="form-control"
              name="rollenZugehoerigkeiten"
              id="rezertifizierung-rollenZugehoerigkeiten"
              data-cy="rollenZugehoerigkeiten"
              :class="{
                valid: !$v.rezertifizierung.rollenZugehoerigkeiten.$invalid,
                invalid: $v.rezertifizierung.rollenZugehoerigkeiten.$invalid,
              }"
              v-model="$v.rezertifizierung.rollenZugehoerigkeiten.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('svisRezertifizierungApp.rezertifizierung.dvcVertreterNummer')"
              for="rezertifizierung-dvcVertreterNummer"
              >Dvc Vertreter Nummer</label
            >
            <input
              type="text"
              class="form-control"
              name="dvcVertreterNummer"
              id="rezertifizierung-dvcVertreterNummer"
              data-cy="dvcVertreterNummer"
              :class="{ valid: !$v.rezertifizierung.dvcVertreterNummer.$invalid, invalid: $v.rezertifizierung.dvcVertreterNummer.$invalid }"
              v-model="$v.rezertifizierung.dvcVertreterNummer.$model"
            />
            <div v-if="$v.rezertifizierung.dvcVertreterNummer.$anyDirty && $v.rezertifizierung.dvcVertreterNummer.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.rezertifizierung.dvcVertreterNummer.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('svisRezertifizierungApp.rezertifizierung.dvcBenutzerGruppe')"
              for="rezertifizierung-dvcBenutzerGruppe"
              >Dvc Benutzer Gruppe</label
            >
            <input
              type="text"
              class="form-control"
              name="dvcBenutzerGruppe"
              id="rezertifizierung-dvcBenutzerGruppe"
              data-cy="dvcBenutzerGruppe"
              :class="{ valid: !$v.rezertifizierung.dvcBenutzerGruppe.$invalid, invalid: $v.rezertifizierung.dvcBenutzerGruppe.$invalid }"
              v-model="$v.rezertifizierung.dvcBenutzerGruppe.$model"
            />
            <div v-if="$v.rezertifizierung.dvcBenutzerGruppe.$anyDirty && $v.rezertifizierung.dvcBenutzerGruppe.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.rezertifizierung.dvcBenutzerGruppe.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('svisRezertifizierungApp.rezertifizierung.icisSrGebaude')"
              for="rezertifizierung-icisSrGebaude"
              >Icis Sr Gebaude</label
            >
            <input
              type="text"
              class="form-control"
              name="icisSrGebaude"
              id="rezertifizierung-icisSrGebaude"
              data-cy="icisSrGebaude"
              :class="{ valid: !$v.rezertifizierung.icisSrGebaude.$invalid, invalid: $v.rezertifizierung.icisSrGebaude.$invalid }"
              v-model="$v.rezertifizierung.icisSrGebaude.$model"
            />
            <div v-if="$v.rezertifizierung.icisSrGebaude.$anyDirty && $v.rezertifizierung.icisSrGebaude.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.rezertifizierung.icisSrGebaude.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('svisRezertifizierungApp.rezertifizierung.icisSrHaftpflicht')"
              for="rezertifizierung-icisSrHaftpflicht"
              >Icis Sr Haftpflicht</label
            >
            <input
              type="text"
              class="form-control"
              name="icisSrHaftpflicht"
              id="rezertifizierung-icisSrHaftpflicht"
              data-cy="icisSrHaftpflicht"
              :class="{ valid: !$v.rezertifizierung.icisSrHaftpflicht.$invalid, invalid: $v.rezertifizierung.icisSrHaftpflicht.$invalid }"
              v-model="$v.rezertifizierung.icisSrHaftpflicht.$model"
            />
            <div v-if="$v.rezertifizierung.icisSrHaftpflicht.$anyDirty && $v.rezertifizierung.icisSrHaftpflicht.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.rezertifizierung.icisSrHaftpflicht.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('svisRezertifizierungApp.rezertifizierung.icisSrLeitungswasser')"
              for="rezertifizierung-icisSrLeitungswasser"
              >Icis Sr Leitungswasser</label
            >
            <input
              type="text"
              class="form-control"
              name="icisSrLeitungswasser"
              id="rezertifizierung-icisSrLeitungswasser"
              data-cy="icisSrLeitungswasser"
              :class="{
                valid: !$v.rezertifizierung.icisSrLeitungswasser.$invalid,
                invalid: $v.rezertifizierung.icisSrLeitungswasser.$invalid,
              }"
              v-model="$v.rezertifizierung.icisSrLeitungswasser.$model"
            />
            <div v-if="$v.rezertifizierung.icisSrLeitungswasser.$anyDirty && $v.rezertifizierung.icisSrLeitungswasser.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.rezertifizierung.icisSrLeitungswasser.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('svisRezertifizierungApp.rezertifizierung.icisSrKfzKasko')"
              for="rezertifizierung-icisSrKfzKasko"
              >Icis Sr Kfz Kasko</label
            >
            <input
              type="text"
              class="form-control"
              name="icisSrKfzKasko"
              id="rezertifizierung-icisSrKfzKasko"
              data-cy="icisSrKfzKasko"
              :class="{ valid: !$v.rezertifizierung.icisSrKfzKasko.$invalid, invalid: $v.rezertifizierung.icisSrKfzKasko.$invalid }"
              v-model="$v.rezertifizierung.icisSrKfzKasko.$model"
            />
            <div v-if="$v.rezertifizierung.icisSrKfzKasko.$anyDirty && $v.rezertifizierung.icisSrKfzKasko.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.rezertifizierung.icisSrKfzKasko.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 100 })"
              >
                This field cannot be longer than 100 characters.
              </small>
            </div>
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
            :disabled="$v.rezertifizierung.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./rezertifizierung-update.component.ts"></script>
