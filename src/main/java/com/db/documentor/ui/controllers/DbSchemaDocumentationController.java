package com.db.documentor.ui.controllers;

import com.db.documentor.metadata.entities.Schema;
import com.db.documentor.services.jdbc.JdbcMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;

@Controller
@ConditionalOnProperty("enable.schema.db.documentation")
public class DbSchemaDocumentationController {

    JdbcMetadataService jdbcMetadataService;

    @ModelAttribute("schema")
    public Schema getMyBean() {
        jdbcMetadataService.checkSchemaMetadata();
        Schema schema = jdbcMetadataService.collectSchemaMetadata();
        return schema;
    }

    @Autowired
    public DbSchemaDocumentationController(JdbcMetadataService jdbcMetadataService) {
        this.jdbcMetadataService = jdbcMetadataService;
    }

    @GetMapping(value = "/schema")
//    @EnableDbDocumentationController
    public String getSchema() throws SQLException {
        return "index";
    }

    @PostMapping(value = "/schema/updated")
    public String updateSchemaMetadata(@ModelAttribute("schema") Schema schema) {
        jdbcMetadataService.save(schema);
        return "redirect:/schema";
    }

}
