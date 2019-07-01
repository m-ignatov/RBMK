package bg.unisofia.fmi.rbmk.enums;

/**
 * Reactor type
 */
public enum Type {

    RBMK("Reaktor Bolshoy Moshchnosti Kanalnyy"),
    PWR("Pressurized water reactor"),
    BWR("Boiling water reactor");

    private String value;

    Type(String value) {
        this.value = value;
    }

    public String getStatus() {
        return value;
    }
}
