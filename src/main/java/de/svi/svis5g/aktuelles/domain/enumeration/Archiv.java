package de.svi.svis5g.aktuelles.domain.enumeration;

/**
 * The Archiv enumeration.
 */
public enum Archiv {
    JA("Ja"),
    NEIN("Nein");

    private final String value;

    Archiv(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
