package de.fw.passwordsafe.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Entry.
 */
@Entity
@Table(name = "entry")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Entry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 250)
    @Column(name = "description", length = 250)
    private String description;

    @NotNull
    @Size(max = 250)
    @Column(name = "login", length = 250, nullable = false)
    private String login;

    @NotNull
    @Size(max = 250)
    @Column(name = "passwort", length = 250, nullable = false)
    private String passwort;

    @NotNull
    @Size(max = 250)
    @Column(name = "passwort_replay", length = 250, nullable = false)
    private String passwortReplay;

    @ManyToOne
    private Group group;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Entry id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public Entry description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogin() {
        return this.login;
    }

    public Entry login(String login) {
        this.setLogin(login);
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswort() {
        return this.passwort;
    }

    public Entry passwort(String passwort) {
        this.setPasswort(passwort);
        return this;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getPasswortReplay() {
        return this.passwortReplay;
    }

    public Entry passwortReplay(String passwortReplay) {
        this.setPasswortReplay(passwortReplay);
        return this;
    }

    public void setPasswortReplay(String passwortReplay) {
        this.passwortReplay = passwortReplay;
    }

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Entry group(Group group) {
        this.setGroup(group);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entry)) {
            return false;
        }
        return id != null && id.equals(((Entry) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Entry{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", login='" + getLogin() + "'" +
            ", passwort='" + getPasswort() + "'" +
            ", passwortReplay='" + getPasswortReplay() + "'" +
            "}";
    }
}
