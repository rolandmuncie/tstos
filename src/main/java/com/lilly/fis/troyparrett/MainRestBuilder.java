package com.lilly.fis.troyparrett;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;
import org.apache.camel.model.rest.RestBindingMode;

public class MainRestBuilder {
 
    private Main main;
 
    public static void main(String[] args) throws Exception {
        MainRestBuilder example = new MainRestBuilder();
        example.boot();
    }
 
    public void boot() throws Exception {
        // create a Main instance
        main = new Main();

        // add routes
        main.addRouteBuilder(new MyRouteBuilder());
        
        // run until you terminate the JVM
        System.out.println("Starting Camel. Use ctrl + c to terminate the JVM.\n");
        main.run();
    }
 
    private static class MyRouteBuilder extends RouteBuilder {
        @Override
        public void configure() throws Exception {
			
			restConfiguration().component("jetty").host("0.0.0.0").port(8000).bindingMode(RestBindingMode.auto).dataFormatProperty("prettyPrint", "true")
			.apiContextPath("/api-doc")
            .apiProperty("api.title", "User API").apiProperty("api.version", "1.2.3")
            // and enable CORS
            .apiProperty("cors", "true");
			
            rest("/say/")
                .produces("text/plain")
                .get("hello")
                    .route()
                    .transform().constant("Hello World!")
                    .endRest();

        }
    }
 
}
