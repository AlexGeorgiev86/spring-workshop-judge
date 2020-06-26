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
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

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

    @Override
    public HomeworkServiceModel findOneWithLowestComments() {
        return this.homeworkRepository.findAll().stream()
                .min(Comparator.comparingInt(a -> a.getComments().size()))
                .map(homework -> this.modelMapper.map(homework, HomeworkServiceModel.class)).orElse(null);

    }

    @Override
    public HomeworkServiceModel findById(String id) {

        return this.homeworkRepository.findById(id)
                .map(homework -> this.modelMapper
                        .map(homework, HomeworkServiceModel.class)).orElse(null);


    }

    @Override
    public String getHomeworks(String id) {

        Collection<Homework> collection = homeworkRepository.findAllByAuthor_Id(id);
        return collection
                .stream()
                .map(h->h.getExercise().getName())
                .collect(Collectors.joining(System.lineSeparator()));

    }
}
