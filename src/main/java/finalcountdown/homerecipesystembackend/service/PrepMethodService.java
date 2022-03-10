package finalcountdown.homerecipesystembackend.service;

import finalcountdown.homerecipesystembackend.model.PrepMethod;
import finalcountdown.homerecipesystembackend.model.Step;
import finalcountdown.homerecipesystembackend.repository.PrepMethodRepository;
import finalcountdown.homerecipesystembackend.repository.StepRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PrepMethodService {

    private final PrepMethodRepository prepMethodRepository;
    private final StepRepository stepRepository;

    public PrepMethodService(PrepMethodRepository prepMethodRepository, StepRepository stepRepository) {
        this.prepMethodRepository = prepMethodRepository;
        this.stepRepository = stepRepository;
    }

    public void savePrepMethod(PrepMethod prepMethod) {
        prepMethodRepository.save(prepMethod);
    }

    public void addStepToPrepMethod(Step step) {
        try {
            var stepObject = stepRepository.getById(step.getId());
            var prepMethodObject = prepMethodRepository.getById(step.getId());
            prepMethodObject.getSteps().add(stepObject);
            prepMethodRepository.save(prepMethodObject);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public List<PrepMethod> readAllPrepMethods() {
        log.info("Trying to read all prep methods in database");
        var result = prepMethodRepository.findAll();
        log.info("PrepMethods found from DB: [{}]", result);
        return result;
    }

    public Optional<PrepMethod> readPrepMethodById(Long id) {
        log.info("trying to read prepMethod by id: [{}]", id);
        var maybePrepMethod = prepMethodRepository.findById(id);
        log.info("found Ingredient: [{}]", maybePrepMethod);
        return maybePrepMethod;
    }

    @Transactional
    public boolean deletePrepMethodById(Long id) {
        log.info("Trying to delete prepMethod entity by id: [{}]", id);

        boolean result = false;
        if (prepMethodRepository.existsById(id)) {
            prepMethodRepository.deleteById(id);
            result = true;
        }
        return result;
    }

    @Transactional
    public boolean updatePrepMethod(Long id, PrepMethod entity) {
        log.info("updating prepMethod: [{}]", entity);
        Optional<PrepMethod> prepMethod = prepMethodRepository.findById(id);
        if (prepMethod.isPresent()) {
            entity.setId(prepMethod.get().getId());
            prepMethodRepository.save(entity);
            return true;
        }
        return false;
    }
}
