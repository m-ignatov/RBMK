package bg.unisofia.fmi.rbmk.enums;

/**
 * Reactor status
 */
public enum Status {

    RUNNING("Running"),
    STOPPED("Stopped");

    private String value;

    Status(String value) {
        this.value = value;
    }

    public String getStatus() {
        return value;
    }
}
