package bg.unisofia.fmi.rbmk.services;

import bg.unisofia.fmi.rbmk.enums.Status;
import bg.unisofia.fmi.rbmk.exceptions.ForbiddenException;
import bg.unisofia.fmi.rbmk.exceptions.NotFoundException;
import bg.unisofia.fmi.rbmk.models.ControlUnit;
import bg.unisofia.fmi.rbmk.models.Reactor;
import bg.unisofia.fmi.rbmk.models.dto.ControlUnitDto;
import bg.unisofia.fmi.rbmk.models.repositories.ControlUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ControlUnitService {

    private final ControlUnitRepository controlUnitRepository;
    private final ReactorService reactorService;

    @Autowired
    public ControlUnitService(ControlUnitRepository controlUnitRepository, ReactorService reactorService) {
        this.controlUnitRepository = controlUnitRepository;
        this.reactorService = reactorService;
    }

    /**
     * Return all control units
     *
     * @return
     */
    public List<ControlUnitDto> fetchAll() {
        List<ControlUnitDto> controlUnitDtos = new ArrayList<>();
        controlUnitRepository.findAll().forEach(reactor -> controlUnitDtos.add(reactor.toDto()));
        return controlUnitDtos;
    }

    /**
     * Return control unit by given id
     *
     * @param id control unit id
     * @return
     */
    public ControlUnitDto fetchOne(Long id) {
        return findControlUnitById(id).toDto();
    }

    /**
     * Create control unit object
     *
     * @param controlUnitDto
     * @return
     */
    public ControlUnitDto create(ControlUnitDto controlUnitDto) {
        ControlUnit controlUnit = controlUnitDto.toEntity();
        controlUnit = controlUnitRepository.save(controlUnit);
        return controlUnit.toDto();
    }

    /**
     * Update control unit by given id and control unit DTO
     *
     * @param id control unit id
     * @param controlUnitDto
     * @return
     */
    public ControlUnitDto update(Long id, ControlUnitDto controlUnitDto) {
        findControlUnitById(id);
        ControlUnit controlUnit = controlUnitDto.toEntity();
        controlUnit = controlUnitRepository.save(controlUnit);
        return controlUnit.toDto();
    }

    /**
     * Delete control unit object by given id
     *
     * @param id control unit id
     */
    public void delete(Long id) {
        ControlUnit controlUnit = findControlUnitById(id);
        controlUnit.getReactors().forEach(reactor -> reactor.setControlUnit(null));
        controlUnitRepository.deleteById(id);
    }

    /**
     * Attaches a reactor to control unit by ids
     *
     * @param id        control unit id
     * @param reactorId reactor id
     * @return
     */
    public ControlUnitDto attachReactor(Long id, Long reactorId) {
        ControlUnit controlUnit = findControlUnitById(id);
        Reactor reactor = reactorService.findReactorById(reactorId);

        if (isReactorAttached(reactor, controlUnit)) {
            throw new ForbiddenException(String.format("Reactor %d is attached already. Please detach it first.", reactorId));
        }

        controlUnit.getReactors().add(reactor);
        reactor.setControlUnit(controlUnit);

        if (reactor.getStatus() == Status.RUNNING) {
            addCapacity(controlUnit, reactor);
        }

        controlUnitRepository.save(controlUnit);
        return controlUnit.toDto();
    }

    /**
     * Detaches a reactor from control unit by ids
     *
     * @param id        control unit id
     * @param reactorId reactor id
     * @return
     */
    public ControlUnitDto detachReactor(Long id, Long reactorId) {
        ControlUnit controlUnit = findControlUnitById(id);
        Reactor reactor = reactorService.findReactorById(reactorId);

        if (!isReactorAttached(reactor, controlUnit)) {
            throw new ForbiddenException(String.format("Reactor %d is not attached to any control unit.", reactorId));
        }

        controlUnit.getReactors().removeIf(reactorEl -> reactorEl.equals(reactor));
        reactor.setControlUnit(null);

        if (reactor.getStatus() == Status.RUNNING) {
            substractCapacity(controlUnit, reactor);
        }

        controlUnitRepository.save(controlUnit);
        return controlUnit.toDto();
    }

    /**
     * Checks if the given reactor is attached to the given control unit
     *
     * @param reactor
     * @param controlUnit
     * @return
     */
    private boolean isReactorAttached(Reactor reactor, ControlUnit controlUnit) {
        ControlUnit reactorControlUnit = reactor.getControlUnit();
        return Objects.nonNull(reactorControlUnit) &&
                controlUnit.getId().equals(reactorControlUnit.getId());
    }

    /**
     * Turn on the reactor for its control unit
     *
     * @param id        control unit id
     * @param reactorId reactor id
     * @return
     */
    public ControlUnitDto turnOn(Long id, Long reactorId) {
        ControlUnit controlUnit = findControlUnitById(id);
        Reactor reactor = reactorService.findReactorById(reactorId);

        if (!isReactorAttached(reactor, controlUnit)) {
            throw new ForbiddenException(String.format("Reactor %d is not attached to control unit %d.", reactorId, id));
        }

        if (reactor.getStatus() != Status.STOPPED) {
            throw new ForbiddenException("Unexpected reactor status.");
        }

        reactor.setStatus(Status.RUNNING);
        addCapacity(controlUnit, reactor);

        controlUnitRepository.save(controlUnit);
        return controlUnit.toDto();
    }

    /**
     * Turns off the reactor for its control unit
     *
     * @param id        control unit id
     * @param reactorId reactor id
     * @return
     */
    public ControlUnitDto turnOff(Long id, Long reactorId) {
        ControlUnit controlUnit = findControlUnitById(id);
        Reactor reactor = reactorService.findReactorById(reactorId);

        if (!isReactorAttached(reactor, controlUnit)) {
            throw new ForbiddenException(String.format("Reactor %d is not attached to control unit %d.", reactorId, id));
        }

        if (reactor.getStatus() != Status.RUNNING) {
            throw new ForbiddenException("Unexpected reactor status.");
        }

        reactor.setStatus(Status.STOPPED);
        substractCapacity(controlUnit, reactor);

        controlUnitRepository.save(controlUnit);
        return controlUnit.toDto();
    }

    /**
     * Adds reactor power to control unit's total power
     *
     * @param controlUnit
     * @param reactor
     */
    private void addCapacity(ControlUnit controlUnit, Reactor reactor) {
        Integer calculatedPower = controlUnit.getCurrentPower() + reactor.getCapacity();
        if (calculatedPower > controlUnit.getCapacity()) {
            throw new ForbiddenException("Power would exceed maximum capacity.");
        }
        controlUnit.setCurrentPower(calculatedPower);
    }

    /**
     * Subtracts reactor power to control unit's total power
     *
     * @param controlUnit
     * @param reactor
     */
    private void substractCapacity(ControlUnit controlUnit, Reactor reactor) {
        Integer calculatedPower = controlUnit.getCurrentPower() - reactor.getCapacity();
        if (calculatedPower > controlUnit.getCapacity()) {
            throw new ForbiddenException("Power would exceed maximum capacity.");
        }
        controlUnit.setCurrentPower(calculatedPower);
    }

    /**
     * Returns control unit by given id if exists or
     * else throws not found exception
     *
     * @param id control unit id
     * @return
     */
    private ControlUnit findControlUnitById(Long id) {
        Optional<ControlUnit> controlUnitOptional = controlUnitRepository.findById(id);
        if (!controlUnitOptional.isPresent()) {
            throw new NotFoundException(String.format("Control unit with id %d not found", id));
        }
        return controlUnitOptional.get();
    }
}
