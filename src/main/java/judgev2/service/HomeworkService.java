package judgev2.service;

import judgev2.model.service.HomeworkServiceModel;
import judgev2.model.service.UserServiceModel;

public interface HomeworkService {

    void addHomework(HomeworkServiceModel homeworkServiceModel, UserServiceModel userServiceModel);
}
