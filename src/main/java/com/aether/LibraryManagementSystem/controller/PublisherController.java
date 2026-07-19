package com.aether.LibraryManagementSystem.controller;

import com.aether.LibraryManagementSystem.entities.Publisher;
import com.aether.LibraryManagementSystem.service.PublisherService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publisher")
@AllArgsConstructor
public class PublisherController {
    private final PublisherService publisherService;

    @PostMapping
    public ResponseEntity<List<Publisher>> addPublishers(@RequestBody List<Publisher> publishers){
        List<Publisher> publisherList=publisherService.addAllPublishers(publishers);

        return ResponseEntity.ok(publisherList);
    }

    @GetMapping
    public ResponseEntity<List<Publisher>> getAllPublisher(){

        List<Publisher> publishers=publisherService.getAllPublisher();

        return ResponseEntity.ok(publishers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable Long id){
        Publisher publisher=publisherService.getPublisher(id);

        return ResponseEntity.ok(publisher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePublisher(@PathVariable Long id){
        publisherService.deletePublisher(id);

        return ResponseEntity.ok("Deleted Publisher : "+id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable Long id,
                                                     @RequestBody Publisher publisher){
        Publisher updatePublisher=publisherService.updatePublisher(id,publisher);

        return ResponseEntity.ok(updatePublisher);
    }
}
