package bg.unisofia.fmi.rbmk.services;

import bg.unisofia.fmi.rbmk.exceptions.ForbiddenException;
import bg.unisofia.fmi.rbmk.exceptions.NotFoundException;
import bg.unisofia.fmi.rbmk.models.Reactor;
import bg.unisofia.fmi.rbmk.models.dto.ReactorDto;
import bg.unisofia.fmi.rbmk.models.repositories.ReactorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReactorService {

    private final ReactorRepository reactorRepository;

    @Autowired
    public ReactorService(ReactorRepository reactorRepository) {
        this.reactorRepository = reactorRepository;
    }

    /**
     * Return all reactors
     *
     * @return
     */
    public List<ReactorDto> fetchAll() {
        List<ReactorDto> reactorDtos = new ArrayList<>();
        reactorRepository.findAll().forEach(reactor -> reactorDtos.add(reactor.toDto()));
        return reactorDtos;
    }

    /**
     * Return reactor by given id
     *
     * @param id reactor id
     * @return
     */
    public ReactorDto fetchOne(Long id) {
        return findReactorById(id).toDto();
    }

    /**
     * Create reactor object
     *
     * @param reactorDto
     * @return
     */
    public ReactorDto create(ReactorDto reactorDto) {
        Reactor reactor = reactorDto.toEntity();
        reactorRepository.save(reactor);
        return reactor.toDto();
    }

    /**
     * Update reactor by given id and reactor DTO
     *
     * @param id         reactor id
     * @param reactorDto
     * @return
     */
    public ReactorDto update(Long id, ReactorDto reactorDto) {
        if (!reactorRepository.findById(id).isPresent()) {
            throw new NotFoundException(String.format("Reactor with id %d not found", id));
        }

        Reactor reactor = reactorDto.toEntity();
        reactorRepository.save(reactor);
        return reactor.toDto();
    }

    /**
     * Delete reactor by given id
     *
     * @param id reactor id
     */
    public void delete(Long id) {
        Reactor reactor = findReactorById(id);
        if (Objects.nonNull(reactor.getControlUnit())) {
            throw new ForbiddenException("Detach reactor before deleting it.");
        }
        reactorRepository.deleteById(id);
    }

    /**
     * Returns reactor by given id if exists or
     * else throws not found exception
     *
     * @param id reactor id
     * @return
     */
    Reactor findReactorById(Long id) {
        Optional<Reactor> reactorOptional = reactorRepository.findById(id);
        if (!reactorOptional.isPresent()) {
            throw new NotFoundException(String.format("Reactor with id %d not found", id));
        }
        return reactorOptional.get();
    }
}
