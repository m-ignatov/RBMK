package bg.unisofia.fmi.rbmk.models.repositories;

import bg.unisofia.fmi.rbmk.models.ControlUnit;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CRUD functionality for control unit entity
 */
public interface ControlUnitRepository extends JpaRepository<ControlUnit, Long> {
}
