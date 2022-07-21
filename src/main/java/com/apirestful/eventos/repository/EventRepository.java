package com.apirestful.eventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apirestful.eventos.models.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {

    Event findByid(int id);
}
