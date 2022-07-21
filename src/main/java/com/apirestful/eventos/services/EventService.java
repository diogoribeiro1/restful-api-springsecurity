package com.apirestful.eventos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.apirestful.eventos.models.Event;
import com.apirestful.eventos.repository.EventRepository;

@Service
public class EventService {
    
    @Autowired
    EventRepository repository;

    public Event saveEvent(Event event) {
		return repository.save(event);
	}

	public List<Event> listAllEvents() {
		return repository.findAll();
	}

	public ResponseEntity<Event> findEventById(int id) {
		return repository.findById(id).map(event -> ResponseEntity.ok().body(event))
				.orElse(ResponseEntity.notFound().build());
	}

	public ResponseEntity<Event> updateEventById(Event event, int id) {
		return repository.findById(id).map(eventToUpdate -> {
			eventToUpdate.setName(event.getName());
			eventToUpdate.setDate(event.getDate());
			eventToUpdate.setLocale(event.getLocale());
			Event updated = repository.save(eventToUpdate);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	public ResponseEntity<Object> deleteById(int id) {
		return repository.findById(id).map(taskToDelete -> {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		}).orElse(ResponseEntity.notFound().build());

	}
}
