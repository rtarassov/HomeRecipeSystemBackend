package finalcountdown.homerecipesystembackend.controller;

import finalcountdown.homerecipesystembackend.model.Step;
import finalcountdown.homerecipesystembackend.service.StepService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/step")
public class StepController {
    private final StepService stepService;

    @Autowired
    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    @PostMapping("/create-step")
    public ResponseEntity<Step> createStep(@RequestBody Step newStep) {
        log.info("trying to save Step: [{}], called from controller", newStep);
        stepService.saveStep(newStep);
        return ResponseEntity.created(URI.create("/step/create/%d"
                        .formatted(newStep.getId())))
                .body(newStep);

    }

    @GetMapping("/read-all-steps")
    public List<Step> findAllSteps() {
        log.info("finding all Steps, called from controller");
        return stepService.readAllSteps();
    }

    @PutMapping("/update-step/{id}")
    public ResponseEntity<Step> updateStepById(@PathVariable("id") Long id, @RequestBody Step step) {
        log.info("trying to update Step: [{}], called from controller", id);
        boolean updated = stepService.updateStep(id, step);
        if (updated) {
            return ResponseEntity.created(URI.create("/step/update/%d"
                            .formatted(step.getId())))
                    .body(step);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete-step-by-id/{id}")
    public ResponseEntity<Void> deleteStepById(@PathVariable("id") Long id) {
        log.info("trying to delete Step: [{}], called from controller", id);
        boolean deleted = stepService.deleteStepById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
