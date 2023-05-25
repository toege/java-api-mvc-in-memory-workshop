# Java API Model View Controller Workshop

## Learning Objectives
- Use the Model View Controller architecture to organise source code

## Introduction

We didn't do much to organise our code in the previous exercise - I bet you can imagine how messy it would get building an app with hundreds of classes all in the same package!

To remedy this, we often organise our code into their own packages. This is another form of encapsulation.

One common technique is named "Model, View, Controller". This involves separating our code into "layers".

### Model
This is our data layer. It describes what data looks like (e.g. Users, Cars, Products) and often contains something we call a "Repository" - a class that's responsible for creating and retrieving models. For now this is where out ArrayList of Students might live in yesterday's exercise, the Student class is also associated with this location.

### View
This is what the client interacts with. It could be a HTML page, a terminal output or a JSON response from a RESTful API. With API's, the view (JSON) is a representation of a data model. In our examples yesterday the View is what we see in Insomnia or the browser when the request has completed.

### Controller
This layer is responsible for translating interactions from the View layer into logic in our program. If a user clicks a button on a view, the controller decides what to do with that action. This is the part where the Spring Boot code will live, here we translate the request coming in from Insomnia into a call to the Repository class that we created previously. It should retrieve the information from the model and return the relevant information along with any response codes.

### Example
Consider the scenario below:

- User clicks on a "Create Product" button in a view
- A controller class might validate that the user entered a valid product name before telling the Model layer to create the product
- A repository takes care of creating the Product model in a database and lets the controller know when it's done
- The controller notifies the view that the product was created
- The view displays a "Success!" message to the user

This approach to designing a system allows us to keep things organised and modular with each layer responsible for very specific tasks. If we wanted to change our database later, we'd only need to change the Model layer without touching the Controller or View layers.

## Instructions

1. Fork this repository
2. Clone your fork to your machine
3. Open the project in IntelliJ
4. Run the `Main.main` method to start the api server 

## Implementing the Author API using MVC

Initially things will look very similar to yesterday, except that this time we're going to implement an author ID which works independently of the ArrayList index.

Let's start by defining our `Main` class as we did previously.

```java
package com.booleanuk.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
```

Then we'll define a slightly different Author class to use.

```java
package com.booleanuk.api.requests;

public class Author {
    private static int nextID = 1;

    private int id;
    private String name;
    private String email;

    public Author(String name, String email) {
        this.id = nextID;
        nextID++;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
```

The `nextId` field is static across all instances of the class, so each time we create a new instance of the class, it sets the id for that instance and then increments itself, that way we have a unique identifier which is not dependent on the index of the array list, we could if we wanted use this value as a key for a hashmap to store the Authors in too.

Make an AuthorRepository class, later on this is where we will talk to databases from, but for now this is where we will create our ArrayList of authors and manage them.

```java
package com.booleanuk.api.requests;

import java.util.ArrayList;

public class AuthorRepository {
    private ArrayList<Author> authors;

    public AuthorRepository() {
        this.authors = new ArrayList<>();

        this.authors.add(new Author("JRR Tolkien", "jrr@tolkien.com"));
        this.authors.add(new Author("Charles Dickens", "charles@bleakhouse.com"));
    }
}
```

Also make an AuthorController class, this is where the Spring Boot code will live and where we will instantiate the AuthorRepository instance that we will interact with.

```java
package com.booleanuk.api.requests;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("authors")
public class AuthorController {
    private AuthorRepository theAuthors;

    public AuthorController() {
        this.theAuthors = new AuthorRepository();
    }
}
```

Add a new method to the AuthorRepository class to return the whole list of authors.

```java

    public ArrayList<Author> getAll() {
        return this.authors;
    }
```

The add a Get Mapping to the AuthorController class to call that

```java
    @GetMapping
    public ArrayList<Author> getAll() {
        return this.theAuthors.getAll();
    }
```

Run the code and test your GET request using Insomnia, you should see all of the authors currently in the list.

Now let's add a method to AuthorRepository to return an author if the id is found or null if it isn't.

```java
    public Author getOne(int id) {
        for (Author author : this.authors) {
            if (author.getId() == id) {
                return author;
            }
        }
        return null;
    }
```

We could of course use a Stream object instead of the for loop here. This steps through the list of authors and returns an author if the id matches or null otherwise.

Now add the Get Mapping for a specific ID to AuthorController

```java
    @GetMapping("/{id}")
    public Author getOne(@PathVariable (name = "id") int id) {
        return this.theAuthors.getOne(id);
    }
```

## Exercise

Can you create the rest of the end points in a similar way?

## Dealing with errors

We can throw exceptions to generate our error messages as follows. Let's modify the method to get a single Author's details to throw a NOT_FOUND response if one isn't found.

```java
    @GetMapping("/{id}")
    public Author getOne(@PathVariable (name = "id") int id) {
        Author author = this.theAuthors.getOne(id);
        if (author == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return author;
    }
```

The throw part acts in a similar way to return and will exit us from the method, this time returning the 404 response we would expect. We don't need to modify the AuthorRepository code as this already deals with errors by returning null objects.

## Exercise

Add error return codes to other methods in the AuthorController as applicable.

## Exercise

Create the Publisher API from yesterday using MVC. Add in error codes where applicable.





