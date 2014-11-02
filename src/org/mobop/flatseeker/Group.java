package org.mobop.flatseeker;

import java.util.ArrayList;
import java.util.List;

/**
 * Flatseeker MobOp
 * HES-SO
 * Created by Gregori BURRI, Etienne FRANK on 02.11.2014.
 */

public class Group {

    public String string;
    public final List<String> children = new ArrayList<String>();

    public Group(String string) {
        this.string = string;
    }

} 