package br.com.bcourse.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.com.bcourse.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    public boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);
    Optional<User> findByEmail(String email);
    
    @Query("SELECT e FROM User e JOIN FETCH e.roles WHERE e.username = :username")
    User findByUsername(@Param("username") String username);

    boolean existsByUsername(String username); 
}
