package org.mobop.flatseeker.model;

import java.io.Serializable;
import java.util.*;

public abstract class FlatFinder implements Serializable {
    public abstract Collection<Flat> Find(SearchParams params);
}
