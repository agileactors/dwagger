package com.db.documentor.ui.controllers;

import com.db.documentor.metadata.entities.Schema;
import com.db.documentor.services.jdbc.JdbcMetadataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;

@Controller
public class MainController {

    JdbcMetadataServiceImpl jdbcMetadataService;

    @ModelAttribute("schema")
    public Schema getMyBean() {
        jdbcMetadataService.checkSchemaMetadata();
        Schema schema = jdbcMetadataService.collectSchemaMetadata();
        return schema;
    }

    @Autowired
    public MainController(JdbcMetadataServiceImpl jdbcMetadataService) {
        this.jdbcMetadataService = jdbcMetadataService;
    }

    @GetMapping(value = {"/schema", "/"})
    public ModelAndView getSchema() throws SQLException {

        return new ModelAndView("index");
    }

    @PostMapping(value = "/schema/updated")
    public String updateSchemaMetadata(@ModelAttribute("schema") Schema schema) {
        jdbcMetadataService.save(schema);
        return "redirect:/schema";
    }

}
