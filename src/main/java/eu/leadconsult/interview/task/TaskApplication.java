package eu.leadconsult.interview.task;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@SpringBootApplication
public class TaskApplication {
    @Autowired
    private EntityManager entityManager;

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }

    /**
     * Configuring Spring data rest include entity ids into response's JSON
     * 
     * @return RepositoryRestConfigurer
     */
    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        return RepositoryRestConfigurer.withConfig(config -> config.exposeIdsFor(
                entityManager.getMetamodel().getEntities().stream().map(Type::getJavaType).toArray(Class[]::new)));

    }
}
