package judgev2.service.impl;

import judgev2.model.entity.Exercise;
import judgev2.model.service.ExerciseServiceModel;
import judgev2.repository.ExerciseRepository;
import judgev2.service.ExerciseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ModelMapper modelMapper;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, ModelMapper modelMapper) {
        this.exerciseRepository = exerciseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ExerciseServiceModel addExercise(ExerciseServiceModel exerciseServiceModel) {

        Exercise exercise = this.modelMapper.map(exerciseServiceModel, Exercise.class);

        return this.modelMapper.map(this.exerciseRepository.saveAndFlush(exercise), ExerciseServiceModel.class);
    }
}
