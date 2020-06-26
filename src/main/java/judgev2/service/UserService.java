package judgev2.service;

import judgev2.model.service.UserServiceModel;
import judgev2.model.view.UserProfileViewModel;

import java.util.List;

public interface UserService {

    UserServiceModel  registerUser(UserServiceModel userServiceModel);

    UserServiceModel findByUsername(String username);

    List<String> findAllUsernames();

    void changeRole(String username, String role);

    UserProfileViewModel getById(String id);
}
