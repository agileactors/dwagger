package com.db.documentor.metadata.entities;

import javax.persistence.Column;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@javax.persistence.Table(name = "schema_metadata")
public class Schema {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Short id;

    @OneToMany(mappedBy = "schema", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @javax.persistence.Column(name = "schema_tables")
    private List<Table> tables;
    @Column(name = "schema_name")
    private String name;
    @Column(name = "schema_descr", length = 65555)
    private String description;


    public Schema() {

        tables = new ArrayList<>();
        this.description = "";
    }

    public Schema(String name) {
        this.name = name;
    }

    public Schema(List<Table> tables, String name, String description) {
        this.tables = tables;
        this.name = name;
        this.description = description;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        tables.forEach(table -> table.setSchema(this));
        this.tables.clear();
        this.tables.addAll(tables);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schema schema = (Schema) o;
        return Objects.equals(id, schema.id) && Objects.equals(tables, schema.tables) && Objects.equals(name, schema.name) && Objects.equals(description, schema.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tables, name, description);
    }

    @Override
    public String toString() {

        return "Schema{" +
                "tables=" + tables +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }


}
