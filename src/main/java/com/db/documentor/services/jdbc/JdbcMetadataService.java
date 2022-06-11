package com.db.documentor.services.jdbc;

import com.db.documentor.metadata.entities.Schema;

import java.sql.Connection;

public interface JdbcMetadataService {

    Schema save(Schema schema);

    boolean IfExist(String schemaName);

    void checkSchemaMetadata();

    Schema createSchemaMetadata(Connection connection);

    Schema collectSchemaMetadata(String schemaName);

    Schema collectSchemaMetadata();
}
