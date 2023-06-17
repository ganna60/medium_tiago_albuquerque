package com.tiagoamp.productcatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProductCatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductCatalogApplication.class, args);
	}

	//To add entity ‘id’ in responses,
	// we need to register a configuration Spring bean, that I chose to add in the main class:
//	@Component
//	class ExposeEntityIdRestMvcConfiguration implements RepositoryRestConfigurer {
//
//		@Override
//		public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
//			config.exposeIdsFor(ProductEntity.class);
//		}
//
//	}

}
