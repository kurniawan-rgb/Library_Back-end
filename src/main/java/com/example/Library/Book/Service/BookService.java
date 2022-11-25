package com.example.Library.Book.Service;

import com.example.Library.Book.Model.Book;
import com.example.Library.Book.Model.User;
import com.example.Library.Book.Repository.BookRepository;
import com.example.Library.Book.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Book findById(Long bookId){
        Book book = bookRepository.findById(bookId).get();
        return book;
    }
    public void save(Book book){
        bookRepository.save(book);
    }
    public void saveById(Long bookId){
        bookRepository.save(bookRepository.findById(bookId).get());
    }

    public Book get(Long bookId){
        return bookRepository.findById(bookId).get();
    }

    public List<Book> searchBooks(String title, String author){

        List<Book> searchedBooks = new ArrayList<>();

        if (title != null && author == null) {
            searchedBooks = getByTittle(title);
        } else if (title == null && author != null) {
            searchedBooks = getByAuthor(author);
        } else if (title != null && author != null) {
            searchedBooks = getByTitleAndAuthor(title, author);
        }

        return searchedBooks;
    }

    public List<Book> getByTittle(String title){
        List<Book> books = new ArrayList<>();
        for (Book book : bookRepository.findAll()){
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())){
                books.add(book);
            }
        }
        return books;
    }

    public List<Book> getByAuthor(String author){
        List<Book> books = new ArrayList<>();
        for (Book book : bookRepository.findAll()){
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())){
                books.add(book);
            }
        }
        return books;
    }

    public List<Book> getByTitleAndAuthor(String title, String author){
        List<Book> books = new ArrayList<>();
        for (Book book : bookRepository.findAll()){
            if (book.getTitle().toLowerCase().contains(title.toLowerCase()) &&
                    book.getAuthor().toLowerCase().contains(author.toLowerCase())){
                books.add(book);
            }
        }
        return books;
    }

    public void delete(Long bookId){
        bookRepository.deleteById(bookId);
    }

    public void deleteById(long bookId) {
        bookRepository.deleteById(bookId);
    }

    public List<Book> getUnprocessedBookReservations(){
        List<Book> unprocessedBookReservations = new ArrayList<Book>();
        for (Book book : bookRepository.findAll()) {
            if (book.getReservedByUser() != null && book.getReadyForPickUp() == false) {
                unprocessedBookReservations.add(book);
            }
        }
        return unprocessedBookReservations;
    }

    public List<Book> getProcessedBookReservations(){
        List<Book> processedBookReservations = new ArrayList<Book>();
        for (Book book : bookRepository.findAll()) {
            if (book.getReservedByUser() != null && book.getReadyForPickUp() == true) {
                processedBookReservations.add(book);
            }
        }
        return processedBookReservations;
    }

    public List<Book> convertIdsCollectionToBooksList(Collection<Long> bookIds){
        List<Book> books = new ArrayList<Book>();
        for (Long bookId : bookIds) books.add(bookRepository.findById(bookId).get());
        return books;
    }

    public void removeCurrentUserOfMultipleBooks(List<Book> books) {
        for (Book book : books) removeCurrentUserOfBook(book);
    }

    public void removeCurrentUserOfBook(Book book) {
        User currentUser = book.getTheUser();
        for (int i = 0; i < currentUser.getBooks().size(); i++) {
            if (currentUser.getBooks().get(i).getBookId() == book.getBookId()) {
                currentUser.getBooks().remove(i);
                break;
            }
        }
        userRepository.save(currentUser);
        book.setTheUser(null);
        book.setReturnDate(null);
        book.setTimesExtended(0);
        bookRepository.save(book);
    }

    public void removeReservation(Book book) {
        User reservedByUser = book.getReservedByUser();
        for (int i = 0; i < reservedByUser.getReservedBooks().size(); i++) {
            if (reservedByUser.getReservedBooks().get(i).getBookId() == book.getBookId()) {
                reservedByUser.getReservedBooks().remove(i);
                break;
            }
        }
        userRepository.save(reservedByUser);
        book.setStartReservationDate(null);
        book.setEndReservationDate(null);
        book.setReadyForPickUp(false);
        bookRepository.save(book);
    }

    public void saveBookOrder(Collection<Long> selectedBookIds, User user) {
        for (Long bookId : selectedBookIds) {
            Book book = bookRepository.findById(bookId).get();
            book.setReturnDate(LocalDate.now().plusDays(20));
            book.setStartReservationDate(null);
            book.setEndReservationDate(null);
            book.setReservedByUser(null);
            book.setReadyForPickUp(false);
            book.setTheUser(user);
            bookRepository.save(book);
            userRepository.save(user);
        }
    }
}
