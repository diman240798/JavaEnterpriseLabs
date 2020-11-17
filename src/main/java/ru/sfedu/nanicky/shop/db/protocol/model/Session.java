package ru.sfedu.nanicky.shop.db.protocol.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.UUID;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Session extends IdEntity {
    private final String session = UUID.randomUUID().toString();
    private final long date = System.currentTimeMillis();

    public Session() {}

    public Session(long id) {
        setId(id);
    }

    public String getSession() {
        return session;
    }

    public long getDate() {
        return date;
    }
}
