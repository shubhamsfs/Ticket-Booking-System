package com.example.demo.JPA;

import com.example.demo.Entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Integer> {
    @Query("select t from Ticket t where t.time =:time")
    List<Ticket> findTicketAtATime(LocalDateTime time);

    @Transactional
    @Modifying
    @Query("update Ticket t set t.time =:new_time where t.id =:given_id")
    void updateTime(LocalDateTime new_time, int given_id);

    @Transactional
    @Modifying
    @Query("update Ticket t set t.expired =:exp where t.id =:given_id")
    void updateExpiry(Boolean exp, int given_id);

    @Query("select count(t.time) from Ticket t where t.time =:new_time")
    int count(LocalDateTime new_time);

}
