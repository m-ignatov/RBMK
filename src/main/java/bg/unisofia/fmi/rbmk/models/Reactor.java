package bg.unisofia.fmi.rbmk.models;

import bg.unisofia.fmi.rbmk.enums.Status;
import bg.unisofia.fmi.rbmk.enums.Type;
import bg.unisofia.fmi.rbmk.models.dto.ReactorDto;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "reactors")
public class Reactor implements Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer manufactureYear;

    @Column
    private Integer capacity;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne
    @JsonBackReference
    private ControlUnit controlUnit;

    public Reactor() {
    }

    public Reactor(Long id, String name, Integer manufactureYear, Integer capacity, Status status, Type type, ControlUnit controlUnit) {
        this.id = id;
        this.name = name;
        this.manufactureYear = manufactureYear;
        this.capacity = capacity;
        this.status = status;
        this.type = type;
        this.controlUnit = controlUnit;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getManufactureYear() {
        return manufactureYear;
    }

    @Override
    public void setManufactureYear(Integer manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public Integer getCapacity() {
        return capacity;
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

    public ReactorDto toDto() {
        return new ReactorDto(id, name, manufactureYear, capacity, status, type, controlUnit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reactor reactor = (Reactor) o;
        return Objects.equals(id, reactor.id) &&
                Objects.equals(name, reactor.name) &&
                Objects.equals(manufactureYear, reactor.manufactureYear) &&
                Objects.equals(capacity, reactor.capacity) &&
                status == reactor.status &&
                type == reactor.type &&
                Objects.equals(controlUnit, reactor.controlUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, manufactureYear, capacity, status, type, controlUnit);
    }
}
