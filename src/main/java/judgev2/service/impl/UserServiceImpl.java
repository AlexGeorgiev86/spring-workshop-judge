package judgev2.service.impl;

import judgev2.model.entity.Role;
import judgev2.model.entity.User;
import judgev2.model.service.UserServiceModel;
import judgev2.model.view.UserProfileViewModel;
import judgev2.repository.UserRepository;
import judgev2.service.RoleService;
import judgev2.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<String> findAllUsernames() {
        return this.userRepository.findAll()
                .stream().map(User::getUsername).collect(Collectors.toList());
    }

    @Override
    public void changeRole(String username, String role) {
        User user = this.userRepository.findByUsername(username).orElse(null);

        if (!user.getRole().getName().equals(role)) {
            Role roleEntity = this.modelMapper.map(this.roleService.findByName(role), Role.class);
            user.setRole(roleEntity);
            this.userRepository.saveAndFlush(user);
        }
    }

    @Override
    public UserProfileViewModel getById(String id) {
        User user = userRepository.findById(id).orElse(null);
        return user == null ? null : modelMapper.map(user, UserProfileViewModel.class);
    }
}
