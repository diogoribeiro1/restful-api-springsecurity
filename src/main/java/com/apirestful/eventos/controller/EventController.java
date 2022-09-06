package com.apirestful.eventos.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.apirestful.eventos.models.Event;
import com.apirestful.eventos.repository.EventRepository;
import com.apirestful.eventos.services.EventService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/event")
@Api(value = "Api Restfull Eventos")
public class EventController
{

    @Autowired
    private EventService service;

    @Autowired
    private EventRepository repository;

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Retorna uma lista de eventos")
    @GetMapping(produces = "application/json")
    public @ResponseBody ArrayList<Event> listAllEvents()
    {
        List<Event> listEvents = service.listAllEvents();
        ArrayList<Event> eventos = new ArrayList<>();

        for (Event evento : listEvents)
        {
            int codigo = evento.getId();
            evento.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EventController.class).getEventById(codigo))
                    .withSelfRel());
            eventos.add(evento);
        }
        return eventos;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Busca um evento pelo id")
    @GetMapping(value = "/{id}", produces = "application/json")
    public @ResponseBody Event getEventById(@PathVariable(value = "id") int id)
    {
        Event event = service.findEventById(id).getBody();
//        event.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EventController.class).listAllEvents())
//                .withRel("Lista de Eventos"));
        return event;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Salva um Evento")
    @PostMapping()
    public ResponseEntity<Event> saveEvent(@RequestBody @Valid Event event)
    {
        event = service.saveEvent(event);
        int id = event.getId();
        event.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EventController.class).getEventById(id))
                .withSelfRel());
        return ResponseEntity.created(URI.create("/event")).body(event);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Deleta um Evento")
    @DeleteMapping()
    public Event deleteEvent(@RequestBody Event event)
    {
        repository.delete(event);
        return event;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Deleta um Evento pelo id")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteEventById(@PathVariable(value = "id") int id)
    {
        {
             return service.deleteById(id);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Edita um Evento pelo id")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Event> updateEventById(@PathVariable(value = "id") int id, @RequestBody Event event)
    {
		return service.updateEventById(event, id);
	}
}
