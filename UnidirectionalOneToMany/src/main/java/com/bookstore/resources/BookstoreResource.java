package com.bookstore.resources;

import com.bookstore.entity.Author;
import com.bookstore.entity.Book;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("bookstore")
@Stateless
public class BookstoreResource {

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    @GET
    @Path("save")
    public Response addAuthorWithBooks() {

        System.out.println("\n\nPersist author with books ...");

        Author jn = new Author();
        jn.setName("Joana Nimar");
        jn.setAge(34);
        jn.setGenre("History");

        Book jn01 = new Book();
        jn01.setIsbn("001-JN");
        jn01.setTitle("A History of Ancient Prague");

        Book jn02 = new Book();
        jn02.setIsbn("002-JN");
        jn02.setTitle("A People's History");

        Book jn03 = new Book();
        jn03.setIsbn("003-JN");
        jn03.setTitle("World History");

        jn.addBook(jn01);
        jn.addBook(jn02);
        jn.addBook(jn03);

        em.persist(jn);

        return Response
                .ok("Persist done!")
                .build();
    }

    @GET
    @Path("add")
    public Response addNewBook() {

        System.out.println("\n\nPersist a new book to an existing author ...");

        Author author = (Author) em.createQuery("SELECT a FROM Author a WHERE a.name='Joana Nimar'")
                .getSingleResult();

        Book book = new Book();
        book.setIsbn("004-JN");
        book.setTitle("History Details");

        author.addBook(book); // use addBook() helper

        em.persist(author);
        
        return Response
                .ok("New book was added!")
                .build();
    }
    
    @GET
    @Path("removelast")
    public Response deleteLastBook() {

        System.out.println("\n\nRemoving last book ...");
        
        Author author = (Author) em
                .createQuery("SELECT DISTINCT a FROM Author a JOIN FETCH a.books WHERE a.name='Joana Nimar'")
                .getSingleResult();

        Book lastBook = author.getBooks().get(author.getBooks().size() - 1);

        author.removeBook(lastBook);
        
        return Response
                .ok("Last book was removed!")
                .build();
    }
    
    @GET
    @Path("removefirst")
    public Response deleteFirstBook() {

        System.out.println("\n\nRemoving first book ...");
        
        Author author = (Author) em
                .createQuery("SELECT DISTINCT a FROM Author a JOIN FETCH a.books WHERE a.name='Joana Nimar'")
                .getSingleResult();

        Book firstBook = author.getBooks().get(0);

        author.removeBook(firstBook);
        
        return Response
                .ok("First book was removed!")
                .build();
    }
}
