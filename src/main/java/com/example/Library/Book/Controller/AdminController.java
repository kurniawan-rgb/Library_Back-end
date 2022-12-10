package com.example.Library.Book.Controller;


import com.example.Library.Book.Model.User;
import com.example.Library.Book.Security.CurrentUserFinder;
import com.example.Library.Book.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    CurrentUserFinder currentUserFinder;

    @GetMapping
    public String adminHome(Model model) {
        User currentUser = currentUserFinder.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return "admin/admin-home";
    }

    @GetMapping(value="/manageaccounts")
    public String manageAuthorities(@RequestParam(required = false) String firstName,
                                    @RequestParam (required = false) String lastName,
                                    Model model) {
        List<User> users = userService.userSearcher(firstName, lastName);

        model.addAttribute("users", users);
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        return "admin/admin-manage-accounts.html";
    }

    @GetMapping(value="/manageaccount")
    public String manageAccount(@RequestParam Long userId,
                                Model model) {

        User user = userService.findById(userId);
        model.addAttribute("user", user);
        return "/admin/admin-manage-account";
    }

    @PutMapping(value="/confirmaccountsettings")
    public String confirmAccountChanges(@RequestParam boolean accStatus,
                                        @RequestParam String role,
                                        @RequestParam Long userId,
                                        Model model) {
        model.addAttribute("role", role);
        model.addAttribute("accStatus", accStatus);
        model.addAttribute("user", userService.findById(userId));
        return "/admin/admin-confirm-account-settings";
    }

    @PutMapping(value="/saveaccountsettings")
    public String saveAccountSettings(@RequestParam boolean accStatus,
                                      @RequestParam String role,
                                      @RequestParam Long userId) {
        User user = userService.findById(userId);
        user.setRole(role);
        user.setEnabled(accStatus);
        userService.save(user);
        return "redirect:/admin/accountsettingssaved";
    }

    @GetMapping(value="/accountsettingssaved")
    public String accountSettingsSaved() {
        return "/admin/admin-account-settings-saved";
    }
}


