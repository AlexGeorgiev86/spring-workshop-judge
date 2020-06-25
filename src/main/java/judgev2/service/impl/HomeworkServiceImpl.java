package judgev2.service.impl;

import judgev2.model.entity.Exercise;
import judgev2.model.entity.Homework;
import judgev2.model.entity.User;
import judgev2.model.service.HomeworkServiceModel;
import judgev2.model.service.UserServiceModel;
import judgev2.repository.HomeworkRepository;
import judgev2.service.ExerciseService;
import judgev2.service.HomeworkService;
import judgev2.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HomeworkServiceImpl implements HomeworkService {

    private final HomeworkRepository homeworkRepository;
    private final ModelMapper modelMapper;
    private final ExerciseService exerciseService;
    private final UserService userService;

    public HomeworkServiceImpl(HomeworkRepository homeworkRepository, ModelMapper modelMapper, ExerciseService exerciseService, UserService userService) {
        this.homeworkRepository = homeworkRepository;
        this.modelMapper = modelMapper;
        this.exerciseService = exerciseService;
        this.userService = userService;
    }

    @Override
    public void addHomework(HomeworkServiceModel homeworkServiceModel, UserServiceModel userServiceModel) {

        Homework homework = this.modelMapper.map(homeworkServiceModel, Homework.class);
        homework.setExercise(this.modelMapper
                .map(this.exerciseService.findByName(homeworkServiceModel.getExercise()), Exercise.class));
        homework.setAddedOn(LocalDateTime.now());
        homework.setAuthor(this.modelMapper.map(userServiceModel, User.class));
        this.homeworkRepository.saveAndFlush(homework);
    }
}
