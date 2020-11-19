package ru.sfedu.nanicky.shop.db.protocol.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.Objects;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Session)) return false;
        if (!super.equals(o)) return false;
        Session session1 = (Session) o;
        return getDate() == session1.getDate() &&
                Objects.equals(getSession(), session1.getSession());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSession(), getDate());
    }

    @Override
    public String toString() {
        return "Session{" +
                "session='" + session + '\'' +
                ", date=" + date +
                ", id=" + id +
                '}';
    }
}
