package judgev2.service;

import judgev2.model.entity.Homework;
import judgev2.model.service.HomeworkServiceModel;
import judgev2.model.service.UserServiceModel;

public interface HomeworkService {

    void addHomework(HomeworkServiceModel homeworkServiceModel, UserServiceModel userServiceModel);

    HomeworkServiceModel findOneWithLowestComments();

    HomeworkServiceModel findById(String id);

    String getHomeworks(String id);
}
