package de.svi.svis5g.rezertifizierung.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A Rezertifizierung.
 */
@Entity
@Table(name = "rezertifizierung")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rezertifizierung implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 100)
    @Column(name = "login_name", length = 100)
    private String loginName;

    @Size(max = 100)
    @Column(name = "nachname", length = 100)
    private String nachname;

    @Size(max = 100)
    @Column(name = "vorname", length = 100)
    private String vorname;

    @NotNull
    @Column(name = "vermittler_nummer", nullable = false)
    private Integer vermittlerNummer;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "rollen_zugehoerigkeiten")
    private String rollenZugehoerigkeiten;

    @Size(max = 100)
    @Column(name = "dvc_vertreter_nummer", length = 100)
    private String dvcVertreterNummer;

    @Size(max = 100)
    @Column(name = "dvc_benutzer_gruppe", length = 100)
    private String dvcBenutzerGruppe;

    @Size(max = 100)
    @Column(name = "icis_sr_gebaude", length = 100)
    private String icisSrGebaude;

    @Size(max = 100)
    @Column(name = "icis_sr_haftpflicht", length = 100)
    private String icisSrHaftpflicht;

    @Size(max = 100)
    @Column(name = "icis_sr_leitungswasser", length = 100)
    private String icisSrLeitungswasser;

    @Size(max = 100)
    @Column(name = "icis_sr_kfz_kasko", length = 100)
    private String icisSrKfzKasko;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "bestandssicht")
    private String bestandssicht;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "bemerkung")
    private String bemerkung;

    @Column(name = "pruefung_erfolgt")
    private Boolean pruefungErfolgt;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rezertifizierung id(Long id) {
        this.id = id;
        return this;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public Rezertifizierung loginName(String loginName) {
        this.loginName = loginName;
        return this;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getNachname() {
        return this.nachname;
    }

    public Rezertifizierung nachname(String nachname) {
        this.nachname = nachname;
        return this;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getVorname() {
        return this.vorname;
    }

    public Rezertifizierung vorname(String vorname) {
        this.vorname = vorname;
        return this;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public Integer getVermittlerNummer() {
        return this.vermittlerNummer;
    }

    public Rezertifizierung vermittlerNummer(Integer vermittlerNummer) {
        this.vermittlerNummer = vermittlerNummer;
        return this;
    }

    public void setVermittlerNummer(Integer vermittlerNummer) {
        this.vermittlerNummer = vermittlerNummer;
    }

    public String getRollenZugehoerigkeiten() {
        return this.rollenZugehoerigkeiten;
    }

    public Rezertifizierung rollenZugehoerigkeiten(String rollenZugehoerigkeiten) {
        this.rollenZugehoerigkeiten = rollenZugehoerigkeiten;
        return this;
    }

    public void setRollenZugehoerigkeiten(String rollenZugehoerigkeiten) {
        this.rollenZugehoerigkeiten = rollenZugehoerigkeiten;
    }

    public String getDvcVertreterNummer() {
        return this.dvcVertreterNummer;
    }

    public Rezertifizierung dvcVertreterNummer(String dvcVertreterNummer) {
        this.dvcVertreterNummer = dvcVertreterNummer;
        return this;
    }

    public void setDvcVertreterNummer(String dvcVertreterNummer) {
        this.dvcVertreterNummer = dvcVertreterNummer;
    }

    public String getDvcBenutzerGruppe() {
        return this.dvcBenutzerGruppe;
    }

    public Rezertifizierung dvcBenutzerGruppe(String dvcBenutzerGruppe) {
        this.dvcBenutzerGruppe = dvcBenutzerGruppe;
        return this;
    }

    public void setDvcBenutzerGruppe(String dvcBenutzerGruppe) {
        this.dvcBenutzerGruppe = dvcBenutzerGruppe;
    }

    public String getIcisSrGebaude() {
        return this.icisSrGebaude;
    }

    public Rezertifizierung icisSrGebaude(String icisSrGebaude) {
        this.icisSrGebaude = icisSrGebaude;
        return this;
    }

    public void setIcisSrGebaude(String icisSrGebaude) {
        this.icisSrGebaude = icisSrGebaude;
    }

    public String getIcisSrHaftpflicht() {
        return this.icisSrHaftpflicht;
    }

    public Rezertifizierung icisSrHaftpflicht(String icisSrHaftpflicht) {
        this.icisSrHaftpflicht = icisSrHaftpflicht;
        return this;
    }

    public void setIcisSrHaftpflicht(String icisSrHaftpflicht) {
        this.icisSrHaftpflicht = icisSrHaftpflicht;
    }

    public String getIcisSrLeitungswasser() {
        return this.icisSrLeitungswasser;
    }

    public Rezertifizierung icisSrLeitungswasser(String icisSrLeitungswasser) {
        this.icisSrLeitungswasser = icisSrLeitungswasser;
        return this;
    }

    public void setIcisSrLeitungswasser(String icisSrLeitungswasser) {
        this.icisSrLeitungswasser = icisSrLeitungswasser;
    }

    public String getIcisSrKfzKasko() {
        return this.icisSrKfzKasko;
    }

    public Rezertifizierung icisSrKfzKasko(String icisSrKfzKasko) {
        this.icisSrKfzKasko = icisSrKfzKasko;
        return this;
    }

    public void setIcisSrKfzKasko(String icisSrKfzKasko) {
        this.icisSrKfzKasko = icisSrKfzKasko;
    }

    public String getBestandssicht() {
        return this.bestandssicht;
    }

    public Rezertifizierung bestandssicht(String bestandssicht) {
        this.bestandssicht = bestandssicht;
        return this;
    }

    public void setBestandssicht(String bestandssicht) {
        this.bestandssicht = bestandssicht;
    }

    public String getBemerkung() {
        return this.bemerkung;
    }

    public Rezertifizierung bemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
        return this;
    }

    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
    }

    public Boolean getPruefungErfolgt() {
        return this.pruefungErfolgt;
    }

    public Rezertifizierung pruefungErfolgt(Boolean pruefungErfolgt) {
        this.pruefungErfolgt = pruefungErfolgt;
        return this;
    }

    public void setPruefungErfolgt(Boolean pruefungErfolgt) {
        this.pruefungErfolgt = pruefungErfolgt;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rezertifizierung)) {
            return false;
        }
        return id != null && id.equals(((Rezertifizierung) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rezertifizierung{" +
            "id=" + getId() +
            ", loginName='" + getLoginName() + "'" +
            ", nachname='" + getNachname() + "'" +
            ", vorname='" + getVorname() + "'" +
            ", vermittlerNummer=" + getVermittlerNummer() +
            ", rollenZugehoerigkeiten='" + getRollenZugehoerigkeiten() + "'" +
            ", dvcVertreterNummer='" + getDvcVertreterNummer() + "'" +
            ", dvcBenutzerGruppe='" + getDvcBenutzerGruppe() + "'" +
            ", icisSrGebaude='" + getIcisSrGebaude() + "'" +
            ", icisSrHaftpflicht='" + getIcisSrHaftpflicht() + "'" +
            ", icisSrLeitungswasser='" + getIcisSrLeitungswasser() + "'" +
            ", icisSrKfzKasko='" + getIcisSrKfzKasko() + "'" +
            ", bestandssicht='" + getBestandssicht() + "'" +
            ", bemerkung='" + getBemerkung() + "'" +
            ", pruefungErfolgt='" + getPruefungErfolgt() + "'" +
            "}";
    }
}
