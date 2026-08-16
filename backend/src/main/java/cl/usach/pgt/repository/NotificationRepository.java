package cl.usach.pgt.repository;

import cl.usach.pgt.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {}

