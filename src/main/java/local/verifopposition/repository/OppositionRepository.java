package local.verifopposition.repository;

import local.verifopposition.entity.Opposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OppositionRepository extends JpaRepository<Opposition, Long> {

    Optional<Opposition> findByNumeroImmatriculation(String numeroImmatriculation);
}
