package ru.sfedu.nanicky.getpetshome.db.protocol.model;

import java.util.List;

public class Animal extends IdEntity {
    private String name;
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

        if (getId() != animal.getId()) return false;
        if (!getName().equals(animal.getName())) return false;
        return getPhotoIds().equals(animal.getPhotoIds());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getPhotoIds().hashCode();
        return result;
    }
}

