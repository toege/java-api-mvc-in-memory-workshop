package com.booleanuk.api.requests.repositories;

import com.booleanuk.api.requests.model.Author;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

public class AuthorRepository {
    private ArrayList<Author> authors;

    public AuthorRepository() {
        this.authors = new ArrayList<>();

        this.authors.add(new Author("Roald Dahl", "roald@dahl.com"));
        this.authors.add(new Author("Jane Auston", "jane@auston.com"));
    }

    public ArrayList<Author> getAll() {
        if (authors.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }

        return authors;
    }

    public Author getOne(int id){
        for(Author author: authors) {
            if (author.getId() == id) {
                return author;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
    }

    public Author create(Author author){
        for (Author atr: authors) {
            if (author.getName().equals(atr.getName())){
                // Teapot is purely for comedy
                throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Not found");
            }
        }
        authors.add(author);
        for (Author atr: authors) {
            if (author.getName().equals(atr.getName())){
                return atr;
            }
        }
        return null;
    }

    public Author update(int id, Author author) {
        for(Author atr: authors) {
            if (atr.getId() == id) {
                atr.setName(author.getName());
                atr.setEmail(author.getEmail());
                return atr;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
    }

    public ArrayList<Author> delete(int id) {
        for(Author author: authors) {
            if (author.getId() == id) {
                authors.remove(author);
                return authors;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
    }

}
