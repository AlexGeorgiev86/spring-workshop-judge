package judgev2.service;

import judgev2.model.service.RoleServiceModel;
import org.springframework.stereotype.Service;

public interface RoleService {

    RoleServiceModel findByName(String name);
}
