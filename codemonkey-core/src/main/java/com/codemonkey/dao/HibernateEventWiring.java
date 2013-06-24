package com.codemonkey.dao;

import javax.annotation.PostConstruct;

import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PostCollectionRecreateEventListener;
import org.hibernate.event.spi.PostCollectionRemoveEventListener;
import org.hibernate.event.spi.PostCollectionUpdateEventListener;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HibernateEventWiring {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private PostInsertEventListener postInsertEventListener;
    
    @Autowired
    private PostUpdateEventListener postUpdateEventListener;
    
    @Autowired
    private PostDeleteEventListener postDeleteEventListener;
    
    @Autowired
    private PostCollectionRecreateEventListener postCollectionRecreateEventListener;
    
    @Autowired
    private PostCollectionUpdateEventListener postCollectionUpdateEventListener;
    
    @Autowired
    private PostCollectionRemoveEventListener postCollectionRemoveEventListener;

    @PostConstruct
    public void registerListeners() {
        EventListenerRegistry registry = ((SessionFactoryImpl) sessionFactory).getServiceRegistry().getService(
        EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.POST_COMMIT_INSERT).appendListener(postInsertEventListener);
        registry.getEventListenerGroup(EventType.POST_COMMIT_UPDATE).appendListener(postUpdateEventListener);
        registry.getEventListenerGroup(EventType.POST_COMMIT_DELETE).appendListener(postDeleteEventListener);
        
        registry.getEventListenerGroup(EventType.POST_COLLECTION_RECREATE).appendListener(postCollectionRecreateEventListener);
        registry.getEventListenerGroup(EventType.POST_COLLECTION_UPDATE).appendListener(postCollectionUpdateEventListener);
        registry.getEventListenerGroup(EventType.POST_COLLECTION_REMOVE).appendListener(postCollectionRemoveEventListener);
    }
}
