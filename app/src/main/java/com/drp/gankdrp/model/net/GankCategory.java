package com.drp.gankdrp.model.net;

import com.drp.gankdrp.model.multitype.GankItem;

import java.util.List;

/**
 * @author KG on 2017/6/13.
 */

public class GankCategory {
    public int count;
    public boolean error;
    public List<GankItem> results;

    @Override
    public String toString() {
        return "GankCategory{" +
                "count=" + count +
                ", error=" + error +
                ", results=" + results +
                '}';
    }
}
