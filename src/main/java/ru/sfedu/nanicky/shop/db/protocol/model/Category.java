package ru.sfedu.nanicky.shop.db.protocol.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.Objects;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Category extends IdEntity {
    private String name;

    public Category() {}

    public Category(long id, String name) {
        setId(id);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return getId() == category.getId() && Objects.equals(getName(), category.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
