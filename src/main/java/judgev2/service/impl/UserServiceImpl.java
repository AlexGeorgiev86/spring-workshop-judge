package judgev2.service.impl;

import judgev2.model.entity.User;
import judgev2.model.service.UserServiceModel;
import judgev2.repository.UserRepository;
import judgev2.service.RoleService;
import judgev2.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final UserRepository userRepository;
    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, RoleService roleService, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {

        userServiceModel.setRole(this.roleService.findByName(
                this.userRepository.count() == 0 ? "ADMIN" : "USER"));

        User user = this.modelMapper.map(userServiceModel, User.class);


        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public UserServiceModel findByUsername(String username) {

        return this.userRepository.findByUsername(username).map(user -> this.modelMapper
        .map(user, UserServiceModel.class)).orElse(null);

    }
}
