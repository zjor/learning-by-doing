package com.github.zjor.lbd.service;

import com.github.zjor.lbd.model.Event;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@Service
public class EventService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Event create(String title, double x, double y) {
        Point point = new Point(
                new CoordinateArraySequence(
                        new Coordinate[]{new Coordinate(x, y)}),
                new GeometryFactory(new PrecisionModel()));
        Event event = new Event(null, title, new Date(), point);
        return em.merge(event);
    }

}
