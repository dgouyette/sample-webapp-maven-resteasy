package com.cestpasdur.samples.restannuaire.resources;

import javax.ws.rs.core.Application;
import java.util.Set;
import java.util.HashSet;

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
