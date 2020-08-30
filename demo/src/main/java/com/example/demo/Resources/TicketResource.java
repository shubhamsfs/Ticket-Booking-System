package com.example.demo.Resources;

import com.example.demo.Entity.Ticket;
import com.example.demo.Entity.TicketTimingUpdate;
import com.example.demo.Entity.User;
import com.example.demo.JPA.TicketRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class TicketResource {
    @Autowired
    TicketRepository repository;

    // 1.
    @ApiOperation(value = "To book a Ticket")
    @PostMapping("/book_ticket")
    public String bookTicket(@RequestBody Ticket ticket){

        if (repository.count(ticket.getTime()) > 20){
            return "Sorry, Can't book your ticket.  Housefull !!";
        }

        repository.save(ticket);

        return "Ticket Booked";
    }

    // 2.
    @ApiOperation(value = "To Update Time for a particular Ticket  ")
    @PutMapping("/update_time/{id}")
    public String updateUser(@RequestBody TicketTimingUpdate time_update,@PathVariable int id)
    {
        repository.updateTime(time_update.getTime(),id);

        return "Time Updated";
    }


    //3.
    @ApiOperation(value = "To view all Tickets at a Particular Time")
    @GetMapping("/view_ticket/{time}")
    public List<Ticket> getTickets(@PathVariable String time){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);

        return repository.findTicketAtATime(dateTime);
    }

    //4.
    @ApiOperation(value = "To cancel (delete) a Ticket")
    @DeleteMapping("/cancel_ticket/{id}")
    public Ticket deleteTicket(@PathVariable int id){

        Ticket ticket = repository.findById(id).get();
        repository.delete(ticket);

        return ticket;
    }

    //5.
    @ApiOperation(value = "To show User details for particular a Ticket and check and set expiry")
    @GetMapping("/user_details/{id}")
    public User getUserDetails(@PathVariable int id){
        Ticket ticket = repository.findById(id).get();

        User user = new User();
        user.setNumber(ticket.getNumber());
        user.setUser_name(ticket.getUser_name());

        LocalDateTime now = LocalDateTime.now();
        long timeDiff = java.time.Duration.between(now, ticket.getTime()).toHours();

        if (timeDiff > 8){
            repository.updateExpiry(true,id);
        }

        return user;
    }

}
