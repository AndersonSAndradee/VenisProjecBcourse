package br.com.bcourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.bcourse.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

}
