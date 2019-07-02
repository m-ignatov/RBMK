package bg.unisofia.fmi.rbmk.models;

import bg.unisofia.fmi.rbmk.enums.Status;
import bg.unisofia.fmi.rbmk.enums.Type;
import bg.unisofia.fmi.rbmk.models.dto.ReactorDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ReactorTest {

    private Long id = 1L;
    private String name = "Reactor";
    private Integer manufactureYear = 1989;
    private Integer capacity = 1024;
    private Status status = Status.RUNNING;
    private Type type = Type.BWR;

    @Test
    public void givenReactorEntity_WhenToDto_ShouldReturnDtoWithSetProperties() {
        ReactorDto reactorDto = new Reactor(id, name, manufactureYear, capacity,
                status, type, new ControlUnit())
                .toDto();

        assertEquals(id, reactorDto.getId());
        assertEquals(name, reactorDto.getName());
        assertEquals(manufactureYear, reactorDto.getManufactureYear());
        assertEquals(capacity, reactorDto.getCapacity());
        assertEquals(status, reactorDto.getStatus());
        assertEquals(type, reactorDto.getType());
    }
}
