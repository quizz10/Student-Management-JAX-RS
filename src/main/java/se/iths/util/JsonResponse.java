package se.iths.util;

public class JsonResponse {
    private int statusCode;
    private String status;
    private String details;

    public JsonResponse(int statusCode, String status, String details) {
        this.statusCode = statusCode;
        this.status = status;
        this.details = details;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
