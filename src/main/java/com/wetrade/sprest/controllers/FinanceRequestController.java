package com.wetrade.sprest.controllers;

import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wetrade.assets.FinanceRequest;
import com.wetrade.common.FabricProxyException;
import com.wetrade.sprest.services.FinanceRequestService;
import com.wetrade.utils.BaseResponse;
import com.wetrade.utils.ResponseStatus;

import org.json.JSONObject;

import spark.Spark;

public class FinanceRequestController {

    private String format = "EEE MMM D HH:mm:ss Z yyy";
    private Gson gson = new GsonBuilder().setDateFormat(format).create();

    public FinanceRequestController(FinanceRequestService service, String identity) {

        Spark.get("/api/financerequests", (req, res) -> {
            res.type("application/json");
            System.out.println(req.body());
            String behalfOfId = new JSONObject(req.body()).getString("behalfOfId");

            BaseResponse response;
            Collection<FinanceRequest> requests;
            try {
                System.out.println(identity + " " + behalfOfId);
                requests = (Collection<FinanceRequest>) service.getFinanceRequests(identity, behalfOfId);
                response = new BaseResponse(ResponseStatus.SUCCESS, gson.toJsonTree(requests));
            } catch (FabricProxyException exception) {
                response = new BaseResponse(ResponseStatus.ERROR, gson.toJsonTree(exception));
            }
            return gson.toJson(response);
        });

        Spark.get("/api/financerequests/:id", (req, res) -> {
            res.type("application/json");
            String id = req.params(":id");

            BaseResponse response;
            FinanceRequest request;
            try {
                request = service.getFinanceRequest(identity, id);
                response = new BaseResponse(ResponseStatus.SUCCESS, gson.toJsonTree(request));
            } catch (FabricProxyException exception) {
                response = new BaseResponse(ResponseStatus.ERROR, gson.toJsonTree(exception));
            }

            return gson.toJson(response);
        });

        Spark.put("/api/financerequests/:id/approve", (req, res) -> {
            res.type("application/json");
            String id = req.params(":id");

            BaseResponse response;
            try {
                service.approveFinanceRequest(identity, id);
                response = new BaseResponse(ResponseStatus.SUCCESS);
            } catch (FabricProxyException exception) {
                response = new BaseResponse(ResponseStatus.ERROR, gson.toJsonTree(exception));
            }

            return gson.toJson(response);
        });

        Spark.put("/api/financerequests/:id/reject", (req, res) -> {
            res.type("application/json");
            String id = req.params(":id");

            BaseResponse response;
            try {
                service.rejectFinanceRequest(identity, id);
                response = new BaseResponse(ResponseStatus.SUCCESS);
            } catch (FabricProxyException exception) {
                response = new BaseResponse(ResponseStatus.ERROR, gson.toJsonTree(exception));
            }

            return gson.toJson(response);
        });
    }
}
