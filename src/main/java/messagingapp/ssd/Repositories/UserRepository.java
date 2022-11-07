package messagingapp.ssd.Repositories;

import messagingapp.ssd.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface UserRepository extends MongoRepository<User, UUID> {
}
