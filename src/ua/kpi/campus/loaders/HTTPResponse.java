package ua.kpi.campus.loaders;

/**
 * Short response from HTTP
 *
 * @author Artur Dzidzoiev
 * @version 12/8/13
 */
public final class HttpResponse {
    private final int statusCode;
    private final String entity;

    public HttpResponse(int statusCode, String entity) {
        this.statusCode = statusCode;
        this.entity = entity;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getEntity() {
        return entity;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "statusCode=" + statusCode +
                ", entity='" + entity + '\'' +
                '}';
    }
}
