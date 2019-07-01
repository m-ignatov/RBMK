package bg.unisofia.fmi.rbmk.controllers;

import bg.unisofia.fmi.rbmk.models.dto.ControlUnitDto;
import bg.unisofia.fmi.rbmk.services.ControlUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static bg.unisofia.fmi.rbmk.controllers.DefaultController.API_VERSION;

@Validated
@RestController
@RequestMapping(ControlUnitController.REACTOR_PATH)
class ControlUnitController {

    static final String REACTOR_PATH = API_VERSION + "/control-units";

    @Autowired
    private ControlUnitService controlUnitService;

    @GetMapping
    public List<ControlUnitDto> fetchAll() {
        return controlUnitService.fetchAll();
    }

    @GetMapping("/{id}")
    public ControlUnitDto fetchOne(@PathVariable @Min(0) Long id) {
        return controlUnitService.fetchOne(id);
    }

    @PostMapping
    public ControlUnitDto create(@Valid @RequestBody ControlUnitDto controlUnitDto) {
        return controlUnitService.create(controlUnitDto);
    }

    @PostMapping("/{id}/attach-reactor/{reactorId}")
    public ControlUnitDto attachReactor(@PathVariable @Min(0) Long id,
                                        @PathVariable @Min(0) Long reactorId) {
        return controlUnitService.attachReactor(id, reactorId);
    }

    @PostMapping("/{id}/detach-reactor/{reactorId}")
    public ControlUnitDto detachReactor(@PathVariable @Min(0) Long id,
                                        @PathVariable @Min(0) Long reactorId) {
        return controlUnitService.detachReactor(id, reactorId);
    }

    @PostMapping("/{id}/turn-on/{reactorId}")
    public ControlUnitDto turnOnReactor(@PathVariable @Min(0) Long id,
                                        @PathVariable @Min(0) Long reactorId) {
        return controlUnitService.turnOn(id, reactorId);
    }

    @PostMapping("/{id}/turn-off/{reactorId}")
    public ControlUnitDto turnOffReactor(@PathVariable @Min(0) Long id,
                                        @PathVariable @Min(0) Long reactorId) {
        return controlUnitService.turnOff(id, reactorId);
    }

    @PutMapping("/{id}")
    public ControlUnitDto update(@PathVariable @Min(0) Long id, @Valid @RequestBody ControlUnitDto controlUnitDto) {
        return controlUnitService.update(id, controlUnitDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Min(0) Long id) {
        controlUnitService.delete(id);
    }
}
