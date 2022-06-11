package com.db.documentor.repositories;

import com.db.documentor.metadata.entities.Schema;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface SchemaMetaDataRepository extends JpaRepository<Schema,Short> {

    Schema findOneByName(String name);
}
