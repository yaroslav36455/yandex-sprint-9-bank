package by.tyv.account.repository;

import by.tyv.account.model.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, Long> {
}
