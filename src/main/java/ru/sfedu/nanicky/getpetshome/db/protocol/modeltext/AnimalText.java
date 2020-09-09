package ru.sfedu.nanicky.getpetshome.db.protocol.modeltext;

import ru.sfedu.nanicky.getpetshome.db.protocol.model.IdEntity;

public class AnimalText extends IdEntity {
    private String name;
    private String photoIds;

    public AnimalText() {
    }

    public AnimalText(long id, String name, String photoIds) {
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

    public String getPhotoIds() {
        return photoIds;
    }

    public void setPhotoIds(String photoIds) {
        this.photoIds = photoIds;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnimalText)) return false;

        AnimalText animal = (AnimalText) o;

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

