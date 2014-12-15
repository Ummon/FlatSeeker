package org.mobop.flatseeker.model;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.*;
import org.mobop.flatseeker.model.Flat;
import org.mobop.flatseeker.model.SearchParams;

public abstract class FlatFinder implements Serializable {
    public abstract Collection<Flat> Find(SearchParams params);
}
