package ru.sfedu.nanicky.getpetshome.db.protocol.model.photo;

import ru.sfedu.nanicky.getpetshome.db.protocol.model.IdEntity;

public class Photo extends IdEntity {
    private String path;
    private PhotoType photoType;

    public Photo(long id, String path, PhotoType photoType) {
        this.id = id;
        this.path = path;
        this.photoType = photoType;
    }

    public Photo(String path, PhotoType photoType) {
        this.path = path;
        this.photoType = photoType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public PhotoType getPhotoType() {
        return photoType;
    }

    public void setPhotoType(PhotoType photoType) {
        this.photoType = photoType;
    }
}
