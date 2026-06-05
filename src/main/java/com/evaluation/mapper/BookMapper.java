package com.evaluation.mapper;

import com.evaluation.dto.BookResponseDto;
import com.evaluation.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public static BookResponseDto mapEntityToDto(Book book){
        return new BookResponseDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor().getName(),
                book.getSummary()
        );

    }
}
