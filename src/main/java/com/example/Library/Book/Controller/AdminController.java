package com.example.Library.Book.Controller;


import com.example.Library.Book.Model.Book;
import com.example.Library.Book.Model.User;
import com.example.Library.Book.Security.CurrentUserFinder;
import com.example.Library.Book.Service.BookService;
import com.example.Library.Book.Service.UserService;
import com.example.Library.Book.Tambahan.FineCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Autowired
    CurrentUserFinder currentUserFinder;

    @Autowired
    FineCalculator fineCalculator;

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

    @GetMapping(value="/books/showbooks")
    public String showBooks(Model model,
                            @RequestParam (required=false) String title,
                            @RequestParam (required=false) String author,
                            @RequestParam (required=false) String showAllBooks) {

        List<Book> books;
        if (showAllBooks == null) books = bookService.searchBooks(title, author);
        else books = bookService.findAll();

        model.addAttribute("books", books);
        model.addAttribute("title", title);
        model.addAttribute("author", author);
        model.addAttribute("showAllBooks",showAllBooks);
        return "admin/admin-books.html";
    }

    @GetMapping(value="/users/showusers")
    public String showUsers(Model model,
                            @RequestParam(required=false)String firstName,
                            @RequestParam (required=false)String lastName,
                            @RequestParam (required=false)String showAllUsers) {

        List<User> users = new ArrayList<User>();
        LinkedHashMap<User, BigDecimal> usersAndFines = new LinkedHashMap<User, BigDecimal>();

        if (showAllUsers != null) users = userService.findAll();
        else if (firstName != null || lastName != null) users = userService.userSearcher(firstName, lastName);

        usersAndFines = fineCalculator.getAllUsersWithFines(users);
        model.addAttribute("showAllUsers", showAllUsers);
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("users", users);
        model.addAttribute("usersWithFines", usersAndFines);
        return "admin/admin-users.html";
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


