package com.nini.studentservicesmanagementapp.data.api;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.nini.studentservicesmanagementapp.data.dtos.TimeSlotDto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeSlotsApiService extends ApiService {
    public TimeSlotsApiService(Context context) {
        super(context);
    }

    public void addTimeSlots(List<TimeSlotDto> timeSlotDtoList, final VolleyCallback callback) {
        final String ENDPOINT = "/timeslots";
        final String URL = API_URL + ENDPOINT;

        try {
            String jsonBody = mapper.writeValueAsString(timeSlotDtoList);

            StringRequest stringRequest = makePostStringRequest(URL, jsonBody, new VolleyCallback() {
                @Override
                public void onSuccess(String response) {
                    callback.onSuccess(response);
                }

                @Override
                public void onError(VolleyError error) {
                    callback.onError(error);
                }
            });

            queue.add(stringRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void getTimeSlotById(int timeSlotId, final VolleyCallback callback) {
        final String ENDPOINT = "/timeslots/" + timeSlotId;
        final String URL = API_URL + ENDPOINT;

        StringRequest stringRequest = makeGetStringRequest(URL, new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                callback.onSuccess(response);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }
        });

        queue.add(stringRequest);
    }

    public void getTimeSlots(int serviceType, final VolleyCallback callback) {
        final String ENDPOINT = "/timeslots?serviceType=" + serviceType;
        final String URL = API_URL + ENDPOINT;

        StringRequest stringRequest = makeGetStringRequest(URL, new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                callback.onSuccess(response);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }
        });

        queue.add(stringRequest);
    }

    public void getTimeSlots(
            int serviceType,
            LocalDateTime startDateInclusive,
            LocalDateTime endDateExclusive,
            final VolleyCallback callback) {
        final String ENDPOINT =
                "/timeslots?serviceType="
                        + serviceType
                        + "&startDateInclusive="
                        + startDateInclusive
                        + "&endDateExclusive="
                        + endDateExclusive;
        final String URL = API_URL + ENDPOINT;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL,
                response -> {
                    callback.onSuccess(response);
                },
                error -> {
                    callback.onError(error);
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + authorizationToken);
                        return headers;
            }
        };

        queue.add(stringRequest);
    }

    public void updateTimeSlot(
            TimeSlotDto timeSlotDto,
            int timeSlotId,
            final VolleyCallback callback) {
        final String ENDPOINT = "/timeslots/" + timeSlotId;
        final String URL = API_URL + ENDPOINT;

        try {
            String jsonBody = mapper.writeValueAsString(timeSlotDto);
            StringRequest stringRequest = makePutStringRequest(URL, jsonBody, new VolleyCallback() {
                @Override
                public void onSuccess(String response) {
                    callback.onSuccess(response);
                }

                @Override
                public void onError(VolleyError error) {
                    callback.onError(error);
                }
            });

            queue.add(stringRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
