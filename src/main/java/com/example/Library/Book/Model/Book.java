package com.example.Library.Book.Model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="Books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private String title;
    private String author;
    private int relaseYear;
    private int edition;
    private LocalDate returnDate = null;
    private LocalDate startReservationDate = null;
    private LocalDate endReservationDate = null;
    private int timesExtended = 0;
    private boolean readyForPickUp = false;


    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private User reservedByUser;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private User theUser;

    public Book() {

    }

    public Book(String title,
                String author,
                int relaseYear,
                int edition) {
        this.title = title;
        this.author = author;
        this.relaseYear = relaseYear;
        this.edition = edition;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getRelaseYear() {
        return relaseYear;
    }

    public void setRelaseYear(int relaseYear) {
        this.relaseYear = relaseYear;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDate getStartReservationDate() {
        return startReservationDate;
    }

    public void setStartReservationDate(LocalDate startReservationDate) {
        this.startReservationDate = startReservationDate;
    }

    public LocalDate getEndReservationDate() {
        return endReservationDate;
    }

    public void setEndReservationDate(LocalDate endReservationDate) {
        this.endReservationDate = endReservationDate;
    }

    public int getTimesExtended() {
        return timesExtended;
    }

    public void setTimesExtended(int timesExtended) {
        this.timesExtended = timesExtended;
    }

    public boolean isReadyForPickUp() {
        return readyForPickUp;
    }

    public void setReadyForPickUp(boolean readyForPickUp) {
        this.readyForPickUp = readyForPickUp;
    }

    public User getReservedByUser() {
        return reservedByUser;
    }

    public void setReservedByUser(User reservedByUser) {
        this.reservedByUser = reservedByUser;
    }

    public User getTheUser() {
        return theUser;
    }

    public void setTheUser(User theUser) {
        this.theUser = theUser;
    }

    public boolean getReadyForPickUp() {
        return readyForPickUp;
    }
}
