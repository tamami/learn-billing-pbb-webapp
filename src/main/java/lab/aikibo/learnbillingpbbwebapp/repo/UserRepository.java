package lab.aikibo.learnbillingpbbwebapp.repo;

import lab.aikibo.learnbillingpbbwebapp.entity.DatLoginWebapp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<DatLoginWebapp, Long> {

    Optional<DatLoginWebapp> findOneByUsername(String username);

}
