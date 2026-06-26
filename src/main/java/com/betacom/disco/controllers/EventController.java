package com.betacom.disco.controllers;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.betacom.disco.dtos.EventFilter;
import com.betacom.disco.dtos.EventResponseDto;
import com.betacom.disco.entities.Event;
import com.betacom.disco.mappers.EventMapper;
import com.betacom.disco.services.EventService;



@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    public EventController(EventService eventService, EventMapper eventMapper){
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<EventResponseDto>> findAll(EventFilter filter, @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="2") int size){
        Page<Event> eventPage = eventService.findAll(filter, page, size);
        return ResponseEntity.ok(eventPage.map(eventMapper::toDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> findById(@PathVariable Long id){
        Optional<Event> event = eventService.findById(id);
        if (event.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(eventMapper.toDto(event.get()));
    }

    @PostMapping
    public ResponseEntity<EventResponseDto> createEvent(@RequestBody Event newEvent){
        Event event = eventService.createEvent(newEvent);
        if (event == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(eventMapper.toDto(event));
    }

    @PostMapping("/{id}/uploud-image")
    public ResponseEntity uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile[] images) {
        try {
            eventService.saveEventImages(id, images);
            return ResponseEntity.ok("Images upload succesfully.");
        }catch(IllegalArgumentException error){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
        }catch(Exception error){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload images");
        }
    }


}
