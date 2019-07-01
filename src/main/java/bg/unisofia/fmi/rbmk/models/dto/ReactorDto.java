package bg.unisofia.fmi.rbmk.models.dto;

import bg.unisofia.fmi.rbmk.enums.Status;
import bg.unisofia.fmi.rbmk.enums.Type;
import bg.unisofia.fmi.rbmk.models.ControlUnit;
import bg.unisofia.fmi.rbmk.models.Reactor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReactorDto {

    private Long id;

    @NotBlank(message = "Name should not be empty")
    private String name;

    @Min(value = 0, message = "Manufacture year should be positive number")
    private Integer manufactureYear;

    @Min(value = 0, message = "Power should be positive number")
    private Integer capacity;

    private Status status;

    private Type type;

    @JsonIgnore
    private ControlUnit controlUnit;

    public ReactorDto(Long id, String name, Integer manufactureYear, Integer capacity, Status status, Type type, ControlUnit controlUnit) {
        this.id = id;
        this.name = name;
        this.manufactureYear = manufactureYear;
        this.capacity = capacity;
        this.status = status;
        this.type = type;
        this.controlUnit = controlUnit;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(Integer manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ControlUnit getControlUnit() {
        return controlUnit;
    }

    public void setControlUnit(ControlUnit controlUnit) {
        this.controlUnit = controlUnit;
    }

    public Reactor toEntity() {
        return new Reactor(id, name, manufactureYear, capacity, status, type, controlUnit);
    }
}
