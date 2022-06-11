package com.db.documentor.services.jdbc;

import com.db.documentor.metadata.entities.Column;
import com.db.documentor.metadata.entities.Schema;
import com.db.documentor.metadata.entities.Table;
import com.db.documentor.repositories.SchemaMetaDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Service
public class JdbcMetadataServiceImpl implements JdbcMetadataService {

    private final Logger LOGGER = LoggerFactory.getLogger(JdbcMetadataServiceImpl.class);

    private final SchemaMetaDataRepository schemaMetaDataRepository;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.schema}")
    private String dbSchema;

    @Value("${spring.datasource.username}")
    private String db_username;

    @Value("${spring.datasource.password}")
    private String db_password;

    private Schema schema;

    @Autowired
    public JdbcMetadataServiceImpl(SchemaMetaDataRepository schemaMetaDataRepository) {
        this.schemaMetaDataRepository = schemaMetaDataRepository;
    }

    public void checkSchemaMetadata() {

        try (Connection connection = getConnection()) {

            if (!IfExist(dbSchema)) {
                createSchemaMetadata(connection);
                LOGGER.info("Schema metadata saved successfully");
                return;
            }
            LOGGER.info("Schema exists on database");

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @Override
    public Schema save(Schema schema) {
        return schemaMetaDataRepository.save(schema);
    }

    @Override
    public boolean IfExist(String schemaName) {
        return schemaMetaDataRepository.
                exists(Example.of(
                        new Schema(schemaName),
                        ExampleMatcher.matchingAny()));
    }

    @Override
    public Schema createSchemaMetadata(Connection connection) {
        Schema schema = new Schema();
        schema.setName(dbSchema);
        schema.setDescription("Add Schema Description");
        schema.setTables(getTables(connection));
        return save(schema);
    }

    @Override
    public Schema collectSchemaMetadata(String schemaName) {
        return schemaMetaDataRepository.findOne(Example.of(new Schema(schemaName))).get();
    }

    @Override
    public Schema collectSchemaMetadata() {
        return collectSchemaMetadata(dbSchema);
    }


    private final List<Table> getTables(final Connection connection) {

        List<Table> tables = new ArrayList<>();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData
                    .getTables(dbSchema,
                            "%",
                            "%", new String[]{"TABLE"});

            while (rs.next()) {

                String tableName = rs.getString("TABLE_NAME");
                Table table = new Table();
                table.setName(tableName);
                table.setDescription("Add Table description");
                table.setColumns(getTableColumns(tableName, metaData));
                tables.add(table);
            }
            return (tables);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    private final List<Column> getTableColumns(final String tableName, final DatabaseMetaData metaData) throws SQLException {
        List<Column> columns = new ArrayList<>();
        //    Step 2 Find the tables and for each table find the columns
        ResultSet rs = metaData.getColumns(null, null, tableName, null);
        while (rs.next()) {
            String columnName = rs.getString("COLUMN_NAME");
            Column column = new Column();
            column.setName(columnName);
            column.setDescription("Add column description");
            columns.add(column);
        }
        return (columns);
    }

    private final Connection getConnection() {
        try {
            return DriverManager.getConnection(datasourceUrl, db_username, db_password);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


}
