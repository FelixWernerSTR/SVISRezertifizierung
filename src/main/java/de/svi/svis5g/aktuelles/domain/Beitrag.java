package de.svi.svis5g.aktuelles.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.svi.svis5g.aktuelles.domain.enumeration.Archiv;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * not an ignored comment
 */
@Schema(description = "not an ignored comment")
@Entity
@Table(name = "beitrag")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Beitrag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "content")
    private String content;

    @Size(max = 200)
    @Column(name = "attrib", length = 200)
    private String attrib;

    @Size(max = 70)
    @Column(name = "titel", length = 70)
    private String titel;

    @Size(max = 30)
    @Column(name = "rechte", length = 30)
    private String rechte;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "archiv")
    private Archiv archiv;

    @ManyToOne
    @JsonIgnoreProperties(value = { "thementyp" }, allowSetters = true)
    private Thema thema;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Beitrag id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public Beitrag content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttrib() {
        return this.attrib;
    }

    public Beitrag attrib(String attrib) {
        this.setAttrib(attrib);
        return this;
    }

    public void setAttrib(String attrib) {
        this.attrib = attrib;
    }

    public String getTitel() {
        return this.titel;
    }

    public Beitrag titel(String titel) {
        this.setTitel(titel);
        return this;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getRechte() {
        return this.rechte;
    }

    public Beitrag rechte(String rechte) {
        this.setRechte(rechte);
        return this;
    }

    public void setRechte(String rechte) {
        this.rechte = rechte;
    }

    public LocalDate getValidFrom() {
        return this.validFrom;
    }

    public Beitrag validFrom(LocalDate validFrom) {
        this.setValidFrom(validFrom);
        return this;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return this.validTo;
    }

    public Beitrag validTo(LocalDate validTo) {
        this.setValidTo(validTo);
        return this;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public LocalDate getPublishDate() {
        return this.publishDate;
    }

    public Beitrag publishDate(LocalDate publishDate) {
        this.setPublishDate(publishDate);
        return this;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public Archiv getArchiv() {
        return this.archiv;
    }

    public Beitrag archiv(Archiv archiv) {
        this.setArchiv(archiv);
        return this;
    }

    public void setArchiv(Archiv archiv) {
        this.archiv = archiv;
    }

    public Thema getThema() {
        return this.thema;
    }

    public void setThema(Thema thema) {
        this.thema = thema;
    }

    public Beitrag thema(Thema thema) {
        this.setThema(thema);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Beitrag)) {
            return false;
        }
        return id != null && id.equals(((Beitrag) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Beitrag{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", attrib='" + getAttrib() + "'" +
            ", titel='" + getTitel() + "'" +
            ", rechte='" + getRechte() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", publishDate='" + getPublishDate() + "'" +
            ", archiv='" + getArchiv() + "'" +
            "}";
    }
}
