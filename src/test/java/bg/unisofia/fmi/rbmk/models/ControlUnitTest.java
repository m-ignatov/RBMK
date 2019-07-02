package bg.unisofia.fmi.rbmk.models;

import bg.unisofia.fmi.rbmk.models.dto.ControlUnitDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ControlUnitTest {

    private Long id = 1L;
    private String name = "CU";
    private Integer manufactureYear = 1989;
    private Integer currentPower = 0;
    private Integer capacity = 1024;
    private List<Reactor> reactors = new ArrayList<>();

    @Test
    public void givenControlUnitEntity_WhenToDto_ShouldReturnDtoWithSetProperties() {
        ControlUnitDto controlUnitDto = new ControlUnit(id, name, manufactureYear,
                currentPower, capacity, reactors)
                .toDto();

        assertEquals(id, controlUnitDto.getId());
        assertEquals(name, controlUnitDto.getName());
        assertEquals(manufactureYear, controlUnitDto.getManufactureYear());
        assertEquals(capacity, controlUnitDto.getCapacity());
        assertEquals(currentPower, controlUnitDto.getCurrentPower());
        assertEquals(reactors.size(), controlUnitDto.getReactors().size());
    }
}
