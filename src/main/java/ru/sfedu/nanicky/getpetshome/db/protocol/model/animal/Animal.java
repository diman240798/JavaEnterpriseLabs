package ru.sfedu.nanicky.getpetshome.db.protocol.model.animal;

import ru.sfedu.nanicky.getpetshome.db.protocol.model.IdEntity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Animal extends IdEntity {

    private String name;

    @ElementCollection
    private List<Long> photoIds;

    public Animal() {
    }

    public Animal(long id, String name, List<Long> photoIds) {
        this.id = id;
        this.name = name;
        this.photoIds = photoIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getPhotoIds() {
        return photoIds;
    }

    public void setPhotoIds(List<Long> photoIds) {
        this.photoIds = photoIds;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal)) return false;

        Animal animal = (Animal) o;

        return (getId() == animal.getId()) &&
                (getName().equals(animal.getName())) &&
                animal.getPhotoIds().containsAll(getPhotoIds());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getPhotoIds().hashCode();
        return result;
    }
}

