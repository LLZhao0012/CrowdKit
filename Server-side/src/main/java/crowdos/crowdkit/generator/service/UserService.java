package crowdos.crowdkit.generator.service;

import crowdos.crowdkit.generator.exception.BusinessException;
import crowdos.crowdkit.generator.model.UserModel;

public interface UserService {
    UserModel validateLogin(String name, String encrptPassword) throws BusinessException;

    void register(UserModel userModel) throws BusinessException;
}
