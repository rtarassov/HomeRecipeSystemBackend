package finalcountdown.homerecipesystembackend.service;



import finalcountdown.homerecipesystembackend.model.Unit;
import finalcountdown.homerecipesystembackend.repository.UnitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UnitService {
    private final UnitRepository unitRepository;

    public UnitService(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public void saveUnit(Unit unit) {
        unitRepository.save(unit);
    }


    @Transactional
    public boolean updateUnit(Long id, Unit entity) {
        Optional<Unit> unit = unitRepository.findById(id);
        if (unit.isPresent()) {
            entity.setId(unit.get().getId());
            unitRepository.save(entity);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteUnitById(Long id) {
        boolean result = false;
        if (unitRepository.existsById(id)) {
            unitRepository.deleteById(id);
            result = true;
        }
        return result;
    }

    public Optional<Unit> readUnitsById(Long id) {
        log.info("trying to read units by id: [{}]", id);

        var maybeUnit = unitRepository.findById(id);

        log.info("found Ingredient: [{}]", maybeUnit);
        return maybeUnit;
    }

    public List<Unit> readAllUnits() {
        log.info("Trying to read all units in database");

        var result = unitRepository.findAll();
        log.info("Units saved from DB: " + result);
        return result;
    }
}
