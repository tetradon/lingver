package com.kotlart.lingver.rest;

import com.kotlart.lingver.model.Word;
import com.kotlart.lingver.respository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class WordEndpoint {
    private WordRepository repository;

    @Autowired
    public WordEndpoint(WordRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/words")
    Collection<Word> words() {
        return repository.findAll();
    }
}
