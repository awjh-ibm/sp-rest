package com.wetrade.sprest.controllers;

import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wetrade.common.FabricProxyException;
import com.wetrade.sprest.services.PurchaseOrderService;
import com.wetrade.utils.BaseResponse;
import com.wetrade.utils.ResponseStatus;

import org.json.JSONObject;

import spark.Spark;

public class PurchaseOrderController {
    private String format = "EEE MMM D HH:mm:ss Z yyy";
    private Gson gson = new GsonBuilder().setDateFormat(format).create();

    public PurchaseOrderController(PurchaseOrderService service, String identity) {

        Spark.post("/api/purchaseorders/verify", (req, res) -> {
            res.type("application/json");
            String body = req.body();
            JSONObject purchaseOrder = new JSONObject(body);

            BaseResponse response;

            try {
                boolean isValid = service.verifyPurchaseOrder(purchaseOrder);
                if (!isValid) {
                    throw new FabricProxyException("Invalid purchase order");
                }
                response = new BaseResponse(ResponseStatus.SUCCESS);
            } catch (FabricProxyException exception) {
                response = new BaseResponse(ResponseStatus.ERROR, exception.getMessage());
            }

            return gson.toJson(response);
        });
    }
}
