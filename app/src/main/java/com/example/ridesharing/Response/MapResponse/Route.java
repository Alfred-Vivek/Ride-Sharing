
package com.example.ridesharing.Response.MapResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Route {

    @SerializedName("copyrights")
    @Expose
    public String copyrights;
    @SerializedName("legs")
    @Expose
    public List<Leg> legs = null;
    @SerializedName("overview_polyline")
    @Expose
    public OverviewPolyline overviewPolyline;
    @SerializedName("summary")
    @Expose
    public String summary;
    @SerializedName("warnings")
    @Expose
    public List<Object> warnings = null;
    @SerializedName("waypoint_order")
    @Expose
    public List<Object> waypointOrder = null;

    public String getCopyrights() {
        return copyrights;
    }

    public void setCopyrights(String copyrights) {
        this.copyrights = copyrights;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    public OverviewPolyline getOverviewPolyline() {
        return overviewPolyline;
    }

    public void setOverviewPolyline(OverviewPolyline overviewPolyline) {
        this.overviewPolyline = overviewPolyline;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Object> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }

    public List<Object> getWaypointOrder() {
        return waypointOrder;
    }

    public void setWaypointOrder(List<Object> waypointOrder) {
        this.waypointOrder = waypointOrder;
    }

}
