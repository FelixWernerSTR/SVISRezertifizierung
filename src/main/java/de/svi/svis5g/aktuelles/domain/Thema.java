package de.svi.svis5g.aktuelles.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Thema.
 */
@Entity
@Table(name = "thema")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Thema implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Size(max = 30)
    @Column(name = "rechte", length = 30)
    private String rechte;

    @Min(value = 0L)
    @Max(value = 999L)
    @Column(name = "displaycount")
    private Long displaycount;

    @ManyToOne
    private Thementyp thementyp;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Thema id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Thema name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRechte() {
        return this.rechte;
    }

    public Thema rechte(String rechte) {
        this.setRechte(rechte);
        return this;
    }

    public void setRechte(String rechte) {
        this.rechte = rechte;
    }

    public Long getDisplaycount() {
        return this.displaycount;
    }

    public Thema displaycount(Long displaycount) {
        this.setDisplaycount(displaycount);
        return this;
    }

    public void setDisplaycount(Long displaycount) {
        this.displaycount = displaycount;
    }

    public Thementyp getThementyp() {
        return this.thementyp;
    }

    public void setThementyp(Thementyp thementyp) {
        this.thementyp = thementyp;
    }

    public Thema thementyp(Thementyp thementyp) {
        this.setThementyp(thementyp);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Thema)) {
            return false;
        }
        return id != null && id.equals(((Thema) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Thema{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", rechte='" + getRechte() + "'" +
            ", displaycount=" + getDisplaycount() +
            "}";
    }
}
