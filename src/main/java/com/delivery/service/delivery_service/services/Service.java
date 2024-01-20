package com.delivery.service.delivery_service.services;

import java.util.List;

public interface Service <Entity, T, I> {

    void deleteBy(I id);
    Entity getBy(I id);
    List<Entity> getAll();


}
