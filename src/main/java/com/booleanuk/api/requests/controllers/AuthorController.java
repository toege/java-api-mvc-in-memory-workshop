package com.booleanuk.api.requests.controllers;

import com.booleanuk.api.requests.repositories.AuthorRepository;
import com.booleanuk.api.requests.model.Author;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("authors")
public class AuthorController {
    private AuthorRepository theAuthors;

    public AuthorController() {
        this.theAuthors = new AuthorRepository();
    }

    @GetMapping
    public ArrayList<Author> getAll() {
        return this.theAuthors.getAll();
    }

    @GetMapping("/{id}")
    public Author getOne(@PathVariable int id) {
        return this.theAuthors.getOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author create(@RequestBody Author author) {
        return this.theAuthors.create(author);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Author update(@PathVariable int id, @RequestBody Author author) {
        return this.theAuthors.update(id, author);
    }

    @DeleteMapping("/{id}")
    public ArrayList<Author> delete(@PathVariable int id) {
        return this.theAuthors.delete(id);
    }



}
