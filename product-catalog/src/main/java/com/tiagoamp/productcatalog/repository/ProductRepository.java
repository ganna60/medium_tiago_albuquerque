package com.tiagoamp.productcatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

//@RepositoryRestResource(collectionResourceRel = "product", path = "product")
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
