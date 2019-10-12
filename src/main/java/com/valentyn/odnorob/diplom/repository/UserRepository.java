package com.valentyn.odnorob.diplom.repository;

import com.valentyn.odnorob.diplom.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByEmail(String email);

    User findByNip(String nip);

    User findByPesel(String pesel);

    User findByActivationCode(String code);

    User findById(String id);
}
