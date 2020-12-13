package ru.sfedu.nanicky.shop.db.protocol.model;

import java.util.Objects;

public class IdEntity {
    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdEntity)) return false;
        IdEntity idEntity = (IdEntity) o;
        return getId() == idEntity.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "IdEntity{" +
                "id=" + id +
                '}';
    }
}
