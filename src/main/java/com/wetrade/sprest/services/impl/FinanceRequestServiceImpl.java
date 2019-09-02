package com.wetrade.sprest.services.impl;

import java.util.Arrays;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wetrade.assets.FinanceRequest;
import com.wetrade.common.FabricProxy;
import com.wetrade.common.FabricProxyConfig;
import com.wetrade.common.FabricProxyException;
import com.wetrade.sprest.services.FinanceRequestService;

public class FinanceRequestServiceImpl implements FinanceRequestService {

    private String subContractName = "FinanceRequestContract";
    private FabricProxy proxy;
    String format = "EEE MMM d HH:mm:ss Z yyy";
    private Gson gson = new GsonBuilder().setDateFormat(format).create();
    private String peer;

    public FinanceRequestServiceImpl(FabricProxyConfig config, String peer) throws FabricProxyException {
        this.proxy = new FabricProxy(config);
        this.peer = peer;
    }

    @Override
    public void approveFinanceRequest(String identity, String id) throws Exception {
        String fcn = "approveFinanceRequest";
        this.proxy.submitTransaction(new String[]{peer}, identity, subContractName, fcn, id);
    }

    @Override
    public void rejectFinanceRequest(String identity, String id) throws Exception {
        String fcn = "rejectFinanceRequest";
        this.proxy.submitTransaction(new String[]{peer}, identity, subContractName, fcn, id);
    }

    @Override
    public FinanceRequest getFinanceRequest(String identity, String id) throws Exception {
        String fcn = "getFinanceRequest";
        String response = this.proxy.evaluateTransaction(identity, subContractName, fcn, new String[]{id});

        FinanceRequest request = gson.fromJson(response, FinanceRequest.class);
        return request;
    }

    @Override
    public FinanceRequest getFinanceRequestByHash(String identity, String hash) throws Exception {
        return null;
    }

    @Override
    public Collection<FinanceRequest> getFinanceRequests(String identity, String behalfOfId) throws Exception {
        String fcn = "getFinanceRequests";
        String response = this.proxy.evaluateTransaction(identity, subContractName, fcn, behalfOfId);

        FinanceRequest[] requests = gson.fromJson(response, FinanceRequest[].class);
        return Arrays.asList(requests);
    }
}
