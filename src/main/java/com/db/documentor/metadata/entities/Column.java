package com.db.documentor.metadata.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@javax.persistence.Table(name = "table_columns")
public class Column {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Short id;

    @javax.persistence.Column(name = "column_name")
    private String name;

    @javax.persistence.Column(name = "column_descr", length = 65555)
    private String description;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id")
    private Table table;

    public Column() {
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

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Column column = (Column) o;
        return Objects.equals(name, column.name) && Objects.equals(description, column.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
