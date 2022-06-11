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

    @Autowired
    public MainController(JdbcMetadataServiceImpl jdbcMetadataService) {
        this.jdbcMetadataService = jdbcMetadataService;
    }

    @GetMapping(value = "/schema")
    public ModelAndView index() throws SQLException {
        jdbcMetadataService.checkSchemaMetadata();
        Schema schema = jdbcMetadataService.collectSchemaMetadata();
        return new ModelAndView("index").addObject("schema", schema);
    }

    @PostMapping(value = "/schema")
    public String updateSchemaMetadata(@ModelAttribute("schema") Schema schema) {
        jdbcMetadataService.save(schema);
        return "index";
    }
}
