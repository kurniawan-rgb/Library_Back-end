package com.example.Library.Book.Security;

import com.example.Library.Book.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.Library.Book.Model.User;

@Service
public class CurrentUserFinder {

    @Autowired
    UserService userService;


    public long getCurrentUserId() {
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = details.getUsername();
        long userId = -1;
        for (com.example.Library.Book.Model.User user: userService.findAll()) {
            if (user.getUserName().equals(username)) {
                userId = user.getUserID();
                break;
            }
        }
        return userId;
    }

    public User getCurrentUser() {
        User currentUser = userService.findById(getCurrentUserId());
        return currentUser;
    }
}
