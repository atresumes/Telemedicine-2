package okhttp3.internal.http;

import java.io.IOException;
import java.util.List;
import okhttp3.Connection;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.connection.StreamAllocation;

public final class RealInterceptorChain implements Chain {
    private int calls;
    private final Connection connection;
    private final HttpStream httpStream;
    private final int index;
    private final List<Interceptor> interceptors;
    private final Request request;
    private final StreamAllocation streamAllocation;

    public RealInterceptorChain(List<Interceptor> interceptors, StreamAllocation streamAllocation, HttpStream httpStream, Connection connection, int index, Request request) {
        this.interceptors = interceptors;
        this.connection = connection;
        this.streamAllocation = streamAllocation;
        this.httpStream = httpStream;
        this.index = index;
        this.request = request;
    }

    public Connection connection() {
        return this.connection;
    }

    public StreamAllocation streamAllocation() {
        return this.streamAllocation;
    }

    public HttpStream httpStream() {
        return this.httpStream;
    }

    public Request request() {
        return this.request;
    }

    public Response proceed(Request request) throws IOException {
        return proceed(request, this.streamAllocation, this.httpStream, this.connection);
    }

    public Response proceed(Request request, StreamAllocation streamAllocation, HttpStream httpStream, Connection connection) throws IOException {
        if (this.index >= this.interceptors.size()) {
            throw new AssertionError();
        }
        this.calls++;
        if (this.httpStream != null && !sameConnection(request.url())) {
            throw new IllegalStateException("network interceptor " + this.interceptors.get(this.index - 1) + " must retain the same host and port");
        } else if (this.httpStream == null || this.calls <= 1) {
            RealInterceptorChain next = new RealInterceptorChain(this.interceptors, streamAllocation, httpStream, connection, this.index + 1, request);
            Interceptor interceptor = (Interceptor) this.interceptors.get(this.index);
            Response response = interceptor.intercept(next);
            if (httpStream != null && this.index + 1 < this.interceptors.size() && next.calls != 1) {
                throw new IllegalStateException("network interceptor " + interceptor + " must call proceed() exactly once");
            } else if (response != null) {
                return response;
            } else {
                throw new NullPointerException("interceptor " + interceptor + " returned null");
            }
        } else {
            throw new IllegalStateException("network interceptor " + this.interceptors.get(this.index - 1) + " must call proceed() exactly once");
        }
    }

    private boolean sameConnection(HttpUrl url) {
        return url.host().equals(this.connection.route().address().url().host()) && url.port() == this.connection.route().address().url().port();
    }
}
