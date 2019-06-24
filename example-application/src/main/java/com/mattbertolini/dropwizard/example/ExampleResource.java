package com.mattbertolini.dropwizard.example;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/example")
public class ExampleResource {
    private final ExampleConfiguration exampleConfiguration;

    public ExampleResource(ExampleConfiguration exampleConfiguration) {
        this.exampleConfiguration = exampleConfiguration;
    }
    

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld(@BeanParam RequestBean requestBean) {
        return "Hello World!";
    }

    public static class RequestBean {

        @QueryParam("many")
        private List<Integer> many;
        

        public List<Integer> getMany() {
            return many;
        }

        public void setMany(List<Integer> many) {
            this.many = many;
        }
    }
}
