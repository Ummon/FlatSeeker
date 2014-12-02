package org.mobop.flatseeker.model;

import java.util.*;
import org.mobop.flatseeker.model.Flat;
import org.mobop.flatseeker.model.SearchParams;

public abstract class FlatFinder {
    public abstract Collection<Flat> Find(SearchParams params);
}