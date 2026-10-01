package vn.edu.ute.services;

import vn.edu.ute.dao.IUserDao_24162013;
import vn.edu.ute.dao.UserDaoImpl_24162013;
import vn.edu.ute.entity.Users;

public class UserServiceImpl_24162013 implements IUserService_24162013 {
    
    IUserDao_24162013 userDao = new UserDaoImpl_24162013();

    @Override
    public void insert(Users user) {
        userDao.insert(user);
    }

    @Override
    public void update(Users user) {
        userDao.update(user);
    }

    @Override
    public Users findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public Users findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public boolean login(String username, String password) {
        return userDao.login(username, password);
    }
}
