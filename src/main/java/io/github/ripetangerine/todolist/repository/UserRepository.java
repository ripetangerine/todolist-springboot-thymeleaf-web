package io.github.ripetangerine.todolist.repository;

import io.github.ripetangerine.todolist.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on Ağustos, 2020
 *
 * @author Faruk
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

//    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

}
