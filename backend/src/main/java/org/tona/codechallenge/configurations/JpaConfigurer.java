package org.tona.codechallenge.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages  = "org.tona.codechallenge.persistence")
public interface JpaConfigurer {

}
