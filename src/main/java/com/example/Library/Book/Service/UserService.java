package com.example.Library.Book.Service;


import com.example.Library.Book.Model.User;
import com.example.Library.Book.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public void saveById(Long userId) {
        User user = userRepository.findById(userId).get();
        userRepository.save(user);
    }

    public User findById(long id) {
        User user = userRepository.findById(id).get();
        return user;
    }

    public List<User> findAll(){
        return (List<User>) userRepository.findAll();
    }

    public List<User> userSearcher(String firstName, String lastName){
        if (firstName != null && lastName != null) return getByFullName(firstName, lastName);
        else if (firstName == null && lastName != null) return getByLastName(lastName);
        else if (firstName != null && lastName == null) return getByFirstName(firstName);
        else return new ArrayList<User>();
    }

    public List<User> getByFirstName(String firstName){
        List<User> users = new ArrayList<User>();
        for (User user : userRepository.findAll()) {
            if (user.getFirstName().toLowerCase().contains(firstName.toLowerCase())) {
                users.add(user);
            }
        }
        return users;
    }

    public List<User> getByLastName(String lastName){
        List<User> users = new ArrayList<User>();
        for (User user : userRepository.findAll()) {
            if(user.getLastName().toLowerCase().contains(lastName.toLowerCase())) {
                users.add(user);
            }
        }
        return users;
    }

    public List<User> getByFullName(String firstName, String lastName){
        List<User> users = new ArrayList<User>();
        for (User user : userRepository.findAll()) {
            if (user.getFirstName().toLowerCase().contains(firstName.toLowerCase()) &&
                    user.getLastName().toLowerCase().contains(lastName.toLowerCase())) {
                users.add(user);
            }
        }
        return users;
    }

}
