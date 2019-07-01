package bg.unisofia.fmi.rbmk.models;

import bg.unisofia.fmi.rbmk.models.dto.ControlUnitDto;
import bg.unisofia.fmi.rbmk.models.dto.ReactorDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "control_units")
public class ControlUnit implements Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer manufactureYear;

    @Column
    private Integer currentPower;

    @Column
    private Integer capacity;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "controlUnit")
    private List<Reactor> reactors;

    public ControlUnit() {

    }

    public ControlUnit(Long id, String name, Integer manufactureYear,
                       Integer currentPower, Integer capacity, List<Reactor> reactors) {
        this.id = id;
        this.name = name;
        this.manufactureYear = manufactureYear;
        this.currentPower = currentPower;
        this.capacity = capacity;
        this.reactors = reactors;
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

    public Integer getCurrentPower() {
        return currentPower;
    }

    public void setCurrentPower(Integer currentPower) {
        this.currentPower = currentPower;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public List<Reactor> getReactors() {
        return reactors;
    }

    public void setReactors(List<Reactor> reactors) {
        this.reactors = reactors;
    }

    public ControlUnitDto toDto() {
        return new ControlUnitDto(id, name, manufactureYear, currentPower, capacity, reactorsToDto(getReactors()));
    }

    private List<ReactorDto> reactorsToDto(List<Reactor> reactors) {
        List<ReactorDto> reactorDtos = new ArrayList<>();
        reactors.forEach(reactor -> reactorDtos.add(reactor.toDto()));
        return reactorDtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ControlUnit that = (ControlUnit) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(manufactureYear, that.manufactureYear) &&
                Objects.equals(currentPower, that.currentPower) &&
                Objects.equals(capacity, that.capacity) &&
                Objects.equals(reactors, that.reactors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, manufactureYear, currentPower, capacity, reactors);
    }
}
