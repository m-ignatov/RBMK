package bg.unisofia.fmi.rbmk.controllers;

import bg.unisofia.fmi.rbmk.models.dto.ReactorDto;
import bg.unisofia.fmi.rbmk.services.ReactorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static bg.unisofia.fmi.rbmk.controllers.DefaultController.API_VERSION;

@Validated
@RestController
@RequestMapping(ReactorController.REACTOR_PATH)
class ReactorController {

    static final String REACTOR_PATH = API_VERSION + "/reactors";

    @Autowired
    private ReactorService reactorService;

    @GetMapping
    public List<ReactorDto> fetchAll() {
        return reactorService.fetchAll();
    }

    @GetMapping("/{id}")
    public ReactorDto fetchOne(@PathVariable @Min(0) Long id) {
        return reactorService.fetchOne(id);
    }

    @PostMapping
    public ReactorDto create(@Valid @RequestBody ReactorDto reactor) {
        return reactorService.create(reactor);
    }

    @PutMapping("/{id}")
    public ReactorDto update(@PathVariable @Min(0) Long id,
                             @Valid @RequestBody ReactorDto reactor) {
        return reactorService.update(id, reactor);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Min(0) Long id) {
        reactorService.delete(id);
    }
}
