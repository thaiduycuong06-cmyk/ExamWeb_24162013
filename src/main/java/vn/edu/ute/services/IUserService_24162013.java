package vn.edu.ute.services;

import vn.edu.ute.entity.Users;

public interface IUserService_24162013 {
    void insert(Users user);
    void update(Users user);
    Users findByUsername(String username);
    Users findByEmail(String email);
    boolean login(String username, String password);
}
