package finalcountdown.homerecipesystembackend.controller;

import finalcountdown.homerecipesystembackend.model.Ingredient;
import finalcountdown.homerecipesystembackend.model.Unit;
import finalcountdown.homerecipesystembackend.service.IngredientService;
import finalcountdown.homerecipesystembackend.service.UnitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("/unit")
public class UnitController {

        private final UnitService unitService;

        @Autowired
        public UnitController(UnitService unitService) {
            this.unitService = unitService;
        }



        @PostMapping("/create")
        public ResponseEntity<?> createUnit(@RequestBody Unit newUnit) {
            log.info("New units to save", newUnit);
            unitService.saveUnit(newUnit);
            return ResponseEntity.created(URI.create("/unit/create/%d"
                            .formatted(newUnit.getId())))
                    .body(newUnit);
        }

        @GetMapping("/read-all")
        public List<Unit> findAllUnits() {
            log.info("findAllUnits() was called from controller");
            return unitService.readAllUnits();
        }

        @GetMapping("/find-by-id/{id}")
        public ResponseEntity<Unit> findIngredientById(@PathVariable("id") Long unitId) {
            log.info("trying to find ingredient entity by id: [{}]", unitId);
            var unit = unitService.readUnitsById(unitId);
            return unit.map(unit1 -> ResponseEntity.ok(unit1))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }

        // localhost:8080/delete-by-id/1
        @DeleteMapping("/delete-by-id/{id}")
        public ResponseEntity<Void> deleteUnitById(@PathVariable("id") Long id) {
            log.info("trying to delete unit by id: [{}]", id);
            boolean deleted = unitService.deleteUnitById(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        }

        @PutMapping("/update/{id}")
        public ResponseEntity<Unit> updateUnitById(@PathVariable("id") Long id, @RequestBody Unit unit) {
            log.info("updateUnitById() called from controller");
            unitService.updateUnit(id, unit);
            return ResponseEntity.created(URI.create("/ingredient/create/%d"
                            .formatted(unit.getId())))
                    .body(unit);
        }
    }

