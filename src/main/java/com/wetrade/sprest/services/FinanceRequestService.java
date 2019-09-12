package com.wetrade.sprest.services;

import java.util.Collection;

import com.wetrade.assets.FinanceRequest;
import com.wetrade.assets.enums.PurchaseOrderStatus;

public interface FinanceRequestService {
    public Collection<FinanceRequest> getFinanceRequests(String identity, String behalfOfId) throws Exception;

    public FinanceRequest getFinanceRequest(String identity, String id) throws Exception;

    public FinanceRequest getFinanceRequestByHash(String identity, String hash) throws Exception;

    public void approveFinanceRequest(String identity, String id) throws Exception;

    public void rejectFinanceRequest(String identity, String id) throws Exception;

    public boolean verifyPurchaseOrder(String identity, String purchaseOrderId, String sellerId, Double price, String productDescriptor, PurchaseOrderStatus status) throws Exception;
}
