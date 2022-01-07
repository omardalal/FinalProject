package com.example.finalproject;

import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Callback used when performing Get request
 */
public interface RequestCallback {
    void onResponse(ArrayList<JSONObject> response, boolean success);
}