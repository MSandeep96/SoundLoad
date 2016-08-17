package com.sande.soundload.Pojo;

import java.util.List;

/**
 * Created by Sandeep on 18-08-2016.
 */
public class LikesPaginated {
    List<Track> collection;
    String next_href;

    public String getNext_href() {
        return next_href;
    }

    public List<Track> getCollection() {
        return collection;
    }
}
