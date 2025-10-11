package by.tyv.account.repository;

import by.tyv.account.model.entity.CredentialsEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CredentialsRepository extends ReactiveCrudRepository<CredentialsEntity, Long> {
}
