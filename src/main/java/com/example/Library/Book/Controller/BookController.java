package com.example.Library.Book.Controller;


import com.example.Library.Book.Model.Book;
import com.example.Library.Book.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/getAll")
    public List<Book> list(){
        return bookService.findAll();
    }

    @PostMapping("/add")
    public String saveBook(@RequestBody Book book){
        bookService.save(book);
        return "Buku diTambahkan";
    }


}
