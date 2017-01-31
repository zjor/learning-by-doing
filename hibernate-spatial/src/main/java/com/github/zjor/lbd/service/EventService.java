package com.github.zjor.lbd.service;

import com.github.zjor.lbd.model.Event;
import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Slf4j
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

    public List<Event> withinRadius(double centerX, double centerY, double radius) {
        String point = "Point(" + centerX + ' ' + centerY + ")";
        try {
            Geometry geom = (new WKTReader()).read(point);
            return em.createQuery("select e from Event e where distance(e.location, :geom) <= :r", Event.class)
                    .setParameter("geom", geom)
                    .setParameter("r", radius)
                    .getResultList();
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }

    }

}
