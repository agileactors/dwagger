package com.db.documentor.metadata.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@javax.persistence.Table(name = "schema_tables")
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Short id;

    @javax.persistence.Column(name = "table_name")
    private String name;
    @javax.persistence.Column(name = "table_descr", length = 65555)
    private String description;

    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Column> columns;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schema_id")
    private Schema schema;

    public Table() {
        columns = new ArrayList<>();
    }


    public Table(Short id) {
        this.id = id;
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

    public List<Column> getColumns() {

        return columns != null ? columns : new ArrayList<>();
    }

    public void setColumns(List<Column> columns) {
        columns.forEach(column -> column.setTable(this));
        this.columns.clear();
        this.columns.addAll(columns);
    }


    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return name.equals(table.name) && columns.equals(table.columns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, columns);
    }

    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", columns=" + columns +
                '}';
    }


}
