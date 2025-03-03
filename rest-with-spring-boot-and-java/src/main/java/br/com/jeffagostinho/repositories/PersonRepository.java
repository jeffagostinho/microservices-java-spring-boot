package br.com.jeffagostinho.repositories;

import br.com.jeffagostinho.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {}
