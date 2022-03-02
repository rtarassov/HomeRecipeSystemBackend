package finalcountdown.homerecipesystembackend.service;

import finalcountdown.homerecipesystembackend.model.Step;
import finalcountdown.homerecipesystembackend.repository.StepRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StepService {
    private final StepRepository stepRepository;

    public StepService(StepRepository stepRepository) {
        this.stepRepository = stepRepository;
    }

    public void saveStep(Step step) {
        stepRepository.save(step);
    }

    public List<Step> readAllSteps() {
        log.info("Trying to read all steps in database");
        var result = stepRepository.findAll();
        log.info("Steps saved in DB: " + result);

        return result;
    }

    @Transactional
    public boolean updateStep(Long id, Step entity) {
        Optional<Step> step = stepRepository.findById(id);
        if (step.isPresent()) {
            entity.setId(step.get().getId());
            stepRepository.save(entity);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteStepById(Long id) {
        boolean result = false;
        if (stepRepository.existsById(id)) {
            stepRepository.deleteById(id);
            result = true;
        }
        return result;
    }
}
