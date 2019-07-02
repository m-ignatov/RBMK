package bg.unisofia.fmi.rbmk.services;

import bg.unisofia.fmi.rbmk.enums.Status;
import bg.unisofia.fmi.rbmk.enums.Type;
import bg.unisofia.fmi.rbmk.exceptions.ForbiddenException;
import bg.unisofia.fmi.rbmk.exceptions.NotFoundException;
import bg.unisofia.fmi.rbmk.models.ControlUnit;
import bg.unisofia.fmi.rbmk.models.Reactor;
import bg.unisofia.fmi.rbmk.models.repositories.ReactorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ReactorServiceTest {

    private Reactor reactor;

    private Long reactorId = 1L;
    private String reactorName = "Reactor";
    private Integer reactorManufactureYear = 1989;
    private Integer reactorCapacity = 1024;
    private Status reactorStatus = Status.RUNNING;
    private Type reactorType = Type.BWR;

    @InjectMocks
    private ReactorService reactorService;

    @Mock
    private ReactorRepository reactorRepository;

    @Before
    public void setUp() {
        reactor = buildReactor(reactorId, reactorName, reactorManufactureYear,
                reactorCapacity, reactorStatus, reactorType, new ControlUnit());

        when(reactorRepository.findById(reactorId)).thenReturn(Optional.of(reactor));
    }

    @Test
    public void givenReactorWithValidId_WhenFindReactorById_ThenReturnReactor() {
        Reactor reactor = reactorService.findReactorById(reactorId);

        assertEquals(reactorId, reactor.getId());
    }

    @Test(expected = ForbiddenException.class)
    public void givenAttachedReactor_WhenDeleteReactorById_ThenThrowForbiddenException() {
        reactorService.delete(reactorId);
    }

    @Test
    public void givenDetachedReactor_WhenDeleteReactorById_ThenReturnSuccess() {
        reactor.setControlUnit(null);
        reactorService.delete(reactorId);
    }

    private Reactor buildReactor(Long id, String name, Integer manufactureYear, Integer capacity,
                                 Status status, Type type, ControlUnit controlUnit) {
        return new Reactor(id, name, manufactureYear, capacity, status, type, controlUnit);
    }
}
