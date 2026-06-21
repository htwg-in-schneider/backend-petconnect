package de.htwg.in.schneider.petconnect.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.htwg.in.schneider.petconnect.backend.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message>
findByAusschreibungIdAndSenderIdAndReceiverIdOrAusschreibungIdAndSenderIdAndReceiverIdOrderBySentAtAsc(
    Long ausschreibungId1,
    Long sender1,
    Long receiver1,

    Long ausschreibungId2,
    Long sender2,
    Long receiver2
);

    List<Message> findBySenderIdOrReceiverIdOrderBySentAtAsc(
        Long senderId,
        Long receiverId
);
}
