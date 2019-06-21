package bg.unisofia.fmi.rbmk.configuration;

import org.glassfish.jersey.jackson.internal.jackson.jaxrs.annotation.JacksonFeatures;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(JacksonFeatures.class);
    }
}
