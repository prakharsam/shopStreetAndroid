package com.example.coviam.myapp.Model.cart;

import com.example.coviam.myapp.Model.products.GetItemRequest;

import java.util.List;

public class GetCartResponse {

    private boolean success;
    private String message;
    private List<GetItemRequest> items;

    public GetCartResponse(boolean success, String message, List<GetItemRequest> getItemRequestList) {
        this.success = success;
        this.message = message;
        this.items = getItemRequestList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GetItemRequest> getItems() {
        return items;
    }

    public void setItems(List<GetItemRequest> items) {
        this.items = items;
    }
}
