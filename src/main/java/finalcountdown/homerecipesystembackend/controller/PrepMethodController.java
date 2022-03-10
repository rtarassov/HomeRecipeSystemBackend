package finalcountdown.homerecipesystembackend.controller;

import finalcountdown.homerecipesystembackend.model.*;
import finalcountdown.homerecipesystembackend.service.PrepMethodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/prep-method")
public class PrepMethodController {
    private final PrepMethodService prepMethodService;

    public PrepMethodController(PrepMethodService prepMethodService) {
        this.prepMethodService = prepMethodService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPrepMethod(@RequestBody PrepMethod newPrepMethod) {
        log.info("New prepMethod to save", newPrepMethod);
        prepMethodService.savePrepMethod(newPrepMethod);
        return ResponseEntity.created(URI.create("/unit/create/%d"
                        .formatted(newPrepMethod.getId())))
                        .body(newPrepMethod);
    }

    @PostMapping("/add-step-to-prep-method")
    public ResponseEntity<Void> addStepToPrepMethod(Step step) {
        prepMethodService.addStepToPrepMethod(step);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/read-all")
    public List<PrepMethod> findAllPrepMethods() {
        log.info("findAllPrepMethods() was called from controller");
        return prepMethodService.readAllPrepMethods();
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<PrepMethod> findPrepMethodById(@PathVariable("id") Long prepMethodId) {
        log.info("trying to find prepMethod entity by id: [{}]", prepMethodId);
        var prepMethod = prepMethodService.readPrepMethodById(prepMethodId);
        return prepMethod.map(recipe1 -> ResponseEntity.ok(recipe1))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<Void> deletePrepMethodById(@PathVariable("id") Long id) {
        log.info("trying to delete prepMethod by id: [{}]", id);
        boolean deleted = prepMethodService.deletePrepMethodById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PrepMethod> updatePrepMethodById(@PathVariable("id") Long id, @RequestBody PrepMethod prepMethod) {
        log.info("updatePrepMethodById() called from controller");
        boolean updated = prepMethodService.updatePrepMethod(id, prepMethod);
        if (updated) {
            return ResponseEntity
                    .created(URI.create("/prep-method/create/%d"
                    .formatted(prepMethod.getId())))
                    .body(prepMethod);
        }
        return ResponseEntity.notFound().build();
    }
}
