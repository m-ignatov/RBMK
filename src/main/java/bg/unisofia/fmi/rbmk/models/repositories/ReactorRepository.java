package bg.unisofia.fmi.rbmk.models.repositories;

import bg.unisofia.fmi.rbmk.models.Reactor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CRUD functionality for reactor entity
 */
public interface ReactorRepository extends JpaRepository<Reactor, Long> {
}
