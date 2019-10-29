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

        Author author = new Author();
        author.setName("Alicia Tom");
        author.setAge(38);
        author.setGenre("Anthology");

        Book book = new Book();
        book.setIsbn("001-AT");
        book.setTitle("The book of swords");

        author.addBook(book); // use addBook() helper

        em.persist(author);

        return Response
                .ok("Persist done!")
                .build();
    }

    @GET
    @Path("remove")
    public Response removeBookOfAuthor() {

        System.out.println("\n\nRemove book of an author ...");
        Author author = (Author) em
                .createQuery("SELECT a FROM Author a JOIN FETCH a.books WHERE a.name='Alicia Tom'")
                .getSingleResult();

        author.removeBook(author.getBooks().get(0)); // use removeBook() helper                

        return Response
                .ok("Remove done!")
                .build();
    }
    
    @GET
    @Path("removeall")
    public Response removeAllBooksOfAuthor() {
        Author author = (Author) em
                .createQuery("SELECT DISTINCT a FROM Author a JOIN FETCH a.books WHERE a.name='Joana Nimar'")                
                .getSingleResult();
        author.removeBooks(); // use removeBooks() helper  
        
        return Response
                .ok("Remove all done!")
                .build();
    }
}