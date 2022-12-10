package com.example.Library.Book.Tambahan;

import com.example.Library.Book.Model.Book;
import com.example.Library.Book.Model.Notification;
import com.example.Library.Book.Model.User;
import com.example.Library.Book.Service.BookService;
import com.example.Library.Book.Service.NotifacationService;
import com.example.Library.Book.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ApplicationRefresh {
    @Autowired
    BookService bookService;

    @Autowired
    UserService usService;

    @Autowired
    NotifacationService notifService;

    //Removes overdue reservations and notifications.
    public void midnightApplicationRefresher() {

        for (Book book : bookService.findAll()) {
            if (book.getEndReservationDate() != null) {
                if (book.getEndReservationDate().compareTo(LocalDate.now()) == -1) {
                    if (book.getReservedByUser() != null) {
                        User user = book.getReservedByUser();
                        book.setReservedByUser(null);
                        usService.save(user);
                    }
                    book.setStartReservationDate(null);
                    book.setEndReservationDate(null);
                    book.setReadyForPickUp(false);
                    bookService.save(book);
                }
            }
        }

        for (Notification notif : notifService.findAll()) {
            if (notif.getValidUntilDate() != null) {
                if (notif.getValidUntilDate().compareTo(LocalDate.now()) == -1) {
                    notifService.deleteById(notif.getNotificationId());
                }
            }
        }
    }
}
