package ru.sfedu.nanicky.getpetshome.db.protocol.model.animal;

import ru.sfedu.nanicky.getpetshome.db.protocol.model.IdEntity;
import ru.sfedu.nanicky.getpetshome.db.protocol.model.adress.GeoAddress;
import ru.sfedu.nanicky.getpetshome.db.protocol.model.user.User;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class TakenAnimal extends IdEntity {
    private User user;
    private Animal animal;
    private GeoAddress geoAddress;
}
