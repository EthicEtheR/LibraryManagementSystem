package com.aether.LibraryManagementSystem.service;

import com.aether.LibraryManagementSystem.entities.Publisher;
import com.aether.LibraryManagementSystem.repository.PublisherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PublisherService {
    private final PublisherRepository publisherRepository;

   //TODO return Dtos not entites

    public List<Publisher> addAllPublishers(List<Publisher> publishers) {

        return publisherRepository.saveAll(publishers);
    }

    public List<Publisher> getAllPublisher() {

        return publisherRepository.findAll();
    }

    public Publisher getPublisher(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Publisher not foound"));

    }

    public void deletePublisher(Long id) {

        if(publisherRepository.existsById(id))
            publisherRepository.deleteById(id);
        else
         throw new RuntimeException("Publisher not found");
    }

    public Publisher updatePublisher(Long id, Publisher publisher) {
        Publisher updatedPublisher=publisherRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Publisher not found "));

        updatedPublisher.setPublisherName(publisher.getPublisherName());

        return publisherRepository.save(updatedPublisher);
    }
}
