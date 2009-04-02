package com.cestpasdur.samples.restannuaire.resources;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class SampleApplication extends Application {
    private Set<Object> singletons = new HashSet<Object>();
    private Set<Class<?>> empty = new HashSet<Class<?>>();

    public SampleApplication() {
        singletons.add(new ContactResource());
        singletons.add(new HelloResource());
    }

    @Override
    public Set<Class<?>> getClasses() {
        return empty;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
