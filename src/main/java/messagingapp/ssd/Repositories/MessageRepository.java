package messagingapp.ssd.Repositories;

import messagingapp.ssd.Models.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MessageRepository extends MongoRepository<Message, UUID> {
}
