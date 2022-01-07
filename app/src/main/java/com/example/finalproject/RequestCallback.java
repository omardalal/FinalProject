package com.example.finalproject;

import org.json.JSONObject;
import java.util.ArrayList;

public interface RequestCallback {
    void onResponse(ArrayList<JSONObject> response, boolean success);
}
