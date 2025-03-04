package br.com.jeffagostinho.repository;

import br.com.jeffagostinho.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
