package judgev2.service;

import judgev2.model.service.ExerciseServiceModel;

import java.util.List;

public interface ExerciseService {
    ExerciseServiceModel addExercise(ExerciseServiceModel exerciseServiceModel);

    List<String> findAllExercises();
    ExerciseServiceModel findByName(String name);
}
