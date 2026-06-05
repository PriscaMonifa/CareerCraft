package com.evaluation.controller;

import com.evaluation.dto.BookResponseDto;
import com.evaluation.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/book")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    //---EVALUATION API ---

    @GetMapping("/all/for-author")
    public List<BookResponseDto> getBooksByAuthor(Principal principal, @RequestParam(defaultValue = "0", required = false) int page,@RequestParam(defaultValue = "10", required = false) int size){
        String name= principal.getName();
        return bookService.getBooksByAuthor(page,size,name);
    }


}
