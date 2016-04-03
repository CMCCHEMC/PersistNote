
package com.dv.persistnote.base.network.bean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ZHResult {

    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("subscribed")
    @Expose
    private List<Object> subscribed = new ArrayList<Object>();
    @SerializedName("others")
    @Expose
    private List<ZHOther> others = new ArrayList<ZHOther>();

    /**
     * 
     * @return
     *     The limit
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * 
     * @param limit
     *     The limit
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * 
     * @return
     *     The subscribed
     */
    public List<Object> getSubscribed() {
        return subscribed;
    }

    /**
     * 
     * @param subscribed
     *     The subscribed
     */
    public void setSubscribed(List<Object> subscribed) {
        this.subscribed = subscribed;
    }

    /**
     * 
     * @return
     *     The others
     */
    public List<ZHOther> getOthers() {
        return others;
    }

    /**
     * 
     * @param others
     *     The others
     */
    public void setOthers(List<ZHOther> others) {
        this.others = others;
    }

}
