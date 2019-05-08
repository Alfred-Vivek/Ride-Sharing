package com.example.ridesharing.Request;

import java.util.HashMap;
import java.util.List;

public class RouteRequest {
    public List<List<HashMap<String, String>>> routes;
    public List<String> message;

    public List<List<HashMap<String, String>>> getRoutes() {
        return routes;
    }

    public void setRoutes(List<List<HashMap<String, String>>> routes) {
        this.routes = routes;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }
}