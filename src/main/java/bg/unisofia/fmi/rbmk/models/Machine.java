package bg.unisofia.fmi.rbmk.models;

public interface Machine {

    Long getId();

    String getName();

    void setName(String name);

    Integer getManufactureYear();

    void setManufactureYear(Integer manufactureYear);
}
