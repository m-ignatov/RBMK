package bg.unisofia.fmi.rbmk.models.dto;

import bg.unisofia.fmi.rbmk.models.ControlUnit;
import bg.unisofia.fmi.rbmk.models.Reactor;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ControlUnitDto {

    private Long id;

    @NotBlank(message = "Name should not be empty")
    private String name;

    @Min(value = 0, message = "Manufacture year should be positive number")
    private Integer manufactureYear;

    @Min(value = 0, message = "Power should be positive number")
    private Integer currentPower = 0;

    @Min(value = 0, message = "Capacity should be positive number")
    private Integer capacity;

    @Valid
    private List<ReactorDto> reactors;

    public ControlUnitDto() {

    }

    public ControlUnitDto(Long id, String name, Integer manufactureYear, Integer currentPower, Integer capacity, List<ReactorDto> reactors) {
        this.id = id;
        this.name = name;
        this.manufactureYear = manufactureYear;
        this.currentPower = currentPower;
        this.capacity = capacity;
        this.reactors = reactors;
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

    public List<ReactorDto> getReactors() {
        if (reactors == null) {
            reactors = new ArrayList<>();
        }
        return reactors;
    }

    public void setReactors(List<ReactorDto> reactors) {
        this.reactors = reactors;
    }

    private List<Reactor> reactorDtosToEntity(List<ReactorDto> reactorDtos) {
        List<Reactor> reactorList = new ArrayList<>();
        reactorDtos.forEach(reactorDto -> reactorList.add(reactorDto.toEntity()));
        return reactorList;
    }

    public ControlUnit toEntity() {
        return new ControlUnit(id, name, manufactureYear, currentPower, capacity, reactorDtosToEntity(getReactors()));
    }
}
