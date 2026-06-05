package com.evaluation.service;

import com.evaluation.dto.BookResponseDto;
import com.evaluation.mapper.BookMapper;
import com.evaluation.model.Book;
import com.evaluation.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    public List<BookResponseDto> getBooksByAuthor(int page, int size, String name) {
        Pageable pageable=PageRequest.of(page,size);
        List<Book> list=bookRepository.getBooksByAuthor(name,pageable);
        return list.stream()
                .map(BookMapper::mapEntityToDto).toList();

    }
}
