package ivans.start.messager.repositories;

import ivans.start.messager.model.Name;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NameRepository extends JpaRepository<Name, String> {
    Optional<Name> findByName(String name);
}
