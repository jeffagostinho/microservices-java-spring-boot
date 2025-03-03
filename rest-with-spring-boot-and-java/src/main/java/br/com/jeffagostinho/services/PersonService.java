package br.com.jeffagostinho.services;

import br.com.jeffagostinho.exceptions.ResourceNotFoundException;
import br.com.jeffagostinho.model.Person;
import br.com.jeffagostinho.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {
    private final Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository personRepository;

    public Person findById(Long id) {
        logger.info("Method findById was called with id: " + id);

        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
    }

    public List<Person> findAll() {
        logger.info("Find all persons");

        return personRepository.findAll();
    }

    public Person create(Person person) {
        logger.info("Method create was called with person: " + person);

        return personRepository.save(person);
    }

    public Person update(Person person) {
        logger.info("Method update was called with person: " + person);

        Person entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return personRepository.save(entity);
    }

    public void delete(Long id) {
        logger.info("Method delete was called with id: " + id);

        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        personRepository.delete(entity);
    }
}
