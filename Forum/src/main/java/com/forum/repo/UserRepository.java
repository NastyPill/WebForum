package com.forum.repo;

import com.forum.model.ActiveUserModel;
import com.forum.model.UserModel;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    List<UserModel> users;
    List<ActiveUserModel> active;

    public UserRepository(List<UserModel> users) {
        this.users = initUsers();
        this.active = new ArrayList<>();
    }

    public void updateActivity(String username) {
        boolean isActive = false;
        List<ActiveUserModel> toDelete = new ArrayList<>();
        for (ActiveUserModel activeUserModel: active) {
            if(activeUserModel.getUserModel().getUsername().equals(username)) {
                activeUserModel.setLastAction(LocalDateTime.now());
                isActive = true;
            }
            if(activeUserModel.getLastAction().isBefore(LocalDateTime.now().minusSeconds(60))) {
                toDelete.add(activeUserModel);
            }
        }
        if(!isActive) {
            active.add(new ActiveUserModel(users.stream()
                    .filter(x -> x.getUsername().equals(username))
                    .collect(Collectors.toList())
                    .get(0),
                    LocalDateTime.now()));
        }
        for (ActiveUserModel activeUserModel : toDelete) {
            active.remove(activeUserModel);
        }
    }

    private List<UserModel> initUsers() {
        List<UserModel> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(new UserModel("user" + i, "password"));
        }
        return users;
    }

    public UserModel register(UserModel userModel) {
        if(isUserExists(userModel.getUsername())) {
            throw new IllegalArgumentException();
        }

        users.add(userModel);
        return userModel;
    }

    public List<UserModel> getAllRegistered() {
        return users;
    }

    public List<ActiveUserModel> getAllActive() {
        return active;
    }

    private boolean isUserExists(String username) {
        for (UserModel user : users) {
            if(user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}