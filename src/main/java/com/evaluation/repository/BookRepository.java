package com.evaluation.repository;

import com.evaluation.model.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {

    @Query("""
             select b
             from Book b
             where b.author.name=?1
"""

    )
    List<Book> getBooksByAuthor(String name, Pageable pageable);
}
