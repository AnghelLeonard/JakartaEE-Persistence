package com.bookstore.resources;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("bookstore")
@Stateless
public class BookstoreResource {

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    @GET
    @Path("load")    
    public Response loadDataAgainAndAgain() {

        System.out.println("\n\nLoading author with books ...");
        
        Query query = em
                .createQuery("SELECT a FROM Author a JOIN FETCH a.books WHERE a.name='Joana Nimar'");       
        query.setHint("eclipselink.cache-usage", "CheckCacheThenDatabase");
        
        System.out.println(query.getSingleResult());
        
        return Response
                .ok("Load done! Refresh page to load again from cache ...")
                .build();
    }    
}
