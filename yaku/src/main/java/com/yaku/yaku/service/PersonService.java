package com.yaku.yaku.service;

import com.yaku.yaku.model.Person;
import com.yaku.yaku.repository.PersonRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Recherche un utilisateur par son email.
     * @param email l'email de l'utilisateur
     * @return l'utilisateur trouvé ou null si non trouvé
     */
    public Person findByEmail(String email) {
        Optional<Person> userOptional = personRepository.findByEmail(email);
        return userOptional.orElse(null);
    }

    /**
     * Sauvegarde un nouvel utilisateur ou met à jour un utilisateur existant.
     * @param person l'utilisateur à enregistrer
     * @return l'utilisateur enregistré
     */
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }
}
