package bg.unisofia.fmi.rbmk.services;

import bg.unisofia.fmi.rbmk.enums.Status;
import bg.unisofia.fmi.rbmk.enums.Type;
import bg.unisofia.fmi.rbmk.exceptions.ForbiddenException;
import bg.unisofia.fmi.rbmk.models.ControlUnit;
import bg.unisofia.fmi.rbmk.models.Reactor;
import bg.unisofia.fmi.rbmk.models.repositories.ControlUnitRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ControlUnitServiceTest {

    private ControlUnit controlUnit;
    private Reactor reactor;

    private Long controlUnitId = 1L;
    private String controlUnitName = "CU";
    private Integer controlUnitManufactureYear = 1989;
    private Integer controlUnitCurrentPower = 0;
    private Integer controlUnitCapacity = 1024;
    private List<Reactor> reactors;

    private Long reactorId = 1L;
    private String reactorName = "Reactor";
    private Integer reactorManufactureYear = 1989;
    private Integer reactorCapacity = 1024;
    private Status reactorStatus = Status.RUNNING;
    private Type reactorType = Type.BWR;

    @InjectMocks
    private ControlUnitService controlUnitService;

    @Mock
    private ReactorService reactorService;

    @Mock
    private ControlUnitRepository controlUnitRepository;

    @Before
    public void setUp() {
        reactors = new ArrayList<>();

        controlUnit = buildControlUnit(controlUnitId, controlUnitName, controlUnitManufactureYear,
                controlUnitCurrentPower, controlUnitCapacity, reactors);

        reactor = buildReactor(reactorId, reactorName, reactorManufactureYear,
                reactorCapacity, reactorStatus, reactorType, controlUnit);

        when(controlUnitRepository.findById(controlUnitId)).thenReturn(Optional.of(controlUnit));
        when(reactorService.findReactorById(reactorId)).thenReturn(reactor);
    }

    @Test
    public void givenRunningReactor_WhenAttachingReactorToControlUnit_ThenAddReactorPower() {
        reactor.setControlUnit(null);

        controlUnitService.attachReactor(controlUnitId, reactorId);

        assertEquals(controlUnit.getCurrentPower(), controlUnit.getCapacity());
        assertEquals(controlUnit, reactor.getControlUnit());
        assertEquals(Status.RUNNING, reactor.getStatus());
    }

    @Test
    public void givenStoppedReactor_WhenAttachingReactorToControlUnit_ThenDoNotAddReactorPower() {
        reactor.setStatus(Status.STOPPED);
        reactor.setControlUnit(null);

        controlUnitService.attachReactor(controlUnitId, reactorId);

        assertEquals(Integer.valueOf(0), controlUnit.getCurrentPower());
        assertEquals(controlUnit, reactor.getControlUnit());
        assertEquals(Status.STOPPED, reactor.getStatus());
    }

    @Test(expected = ForbiddenException.class)
    public void givenAttachedReactor_WhenAttachingReactorToControlUnit_ThenThrowForbiddenException() {
        controlUnitService.attachReactor(controlUnitId, reactorId);
        controlUnitService.attachReactor(controlUnitId, reactorId);
    }

    @Test(expected = ForbiddenException.class)
    public void givenStoppedAndDetachedReactor_WhenDetachingReactor_ThenThrowForbiddenException() {
        reactor.setStatus(Status.STOPPED);
        reactor.setControlUnit(null);

        controlUnitService.detachReactor(controlUnitId, reactorId);
    }

    @Test
    public void givenRunningAndAttachedReactor_WhenDetachingReactor_ThenReturnSubtractedControlUnitPower() {
        controlUnit.setCurrentPower(controlUnit.getCurrentPower() + reactor.getCapacity());
        reactors.add(reactor);

        controlUnitService.detachReactor(controlUnitId, reactorId);

        assertEquals(Integer.valueOf(0), controlUnit.getCurrentPower());
        assertNull(reactor.getControlUnit());
        assertEquals(Status.RUNNING, reactor.getStatus());
    }

    @Test(expected = ForbiddenException.class)
    public void givenStoppedAndAttachedReactor_WhenStoppingReactor_ThenThrowForbiddenException() {
        reactor.setStatus(Status.STOPPED);
        reactors.add(reactor);

        controlUnitService.turnOff(controlUnitId, reactorId);
    }

    @Test(expected = ForbiddenException.class)
    public void givenRunningAndDetachedReactor_WhenStoppingReactor_ThenThrowForbiddenException() {
        reactor.setControlUnit(null);

        controlUnitService.turnOff(controlUnitId, reactorId);
    }

    @Test
    public void givenRunningAndAttachedReactor_WhenStoppingReactor_ThenStopReactorAndSubtractPowerFromControlUnit() {
        controlUnit.setCurrentPower(controlUnit.getCurrentPower() + reactor.getCapacity());
        reactors.add(reactor);

        controlUnitService.turnOff(controlUnitId, reactorId);

        assertEquals(Integer.valueOf(0), controlUnit.getCurrentPower());
        assertEquals(Status.STOPPED, reactor.getStatus());
    }

    @Test(expected = ForbiddenException.class)
    public void givenRunningAndAttachedReactor_WhenStartingReactor_ThenThrowForbiddenException() {
        reactors.add(reactor);

        controlUnitService.turnOn(controlUnitId, reactorId);
    }

    @Test(expected = ForbiddenException.class)
    public void givenRunningAndDetachedReactor_WhenStartingReactor_ThenThrowForbiddenException() {
        reactor.setStatus(Status.STOPPED);
        reactor.setControlUnit(null);

        controlUnitService.turnOff(controlUnitId, reactorId);
    }

    @Test
    public void givenRunningAndAttachedReactor_WhenStartingReactor_ThenStopReactorAndSubtractPowerFromControlUnit() {
        controlUnit.setCurrentPower(controlUnit.getCurrentPower() + reactor.getCapacity());
        reactors.add(reactor);

        controlUnitService.turnOff(controlUnitId, reactorId);

        assertEquals(Integer.valueOf(0), controlUnit.getCurrentPower());
        assertEquals(Status.STOPPED, reactor.getStatus());
    }

    private Reactor buildReactor(Long id, String name, Integer manufactureYear, Integer capacity,
                                 Status status, Type type, ControlUnit controlUnit) {
        return new Reactor(id, name, manufactureYear, capacity, status, type, controlUnit);
    }

    private ControlUnit buildControlUnit(Long id, String name, Integer manufactureYear,
                                         Integer currentPower, Integer capacity, List<Reactor> reactors) {
        return new ControlUnit(id, name, manufactureYear, currentPower, capacity, reactors);
    }
}
