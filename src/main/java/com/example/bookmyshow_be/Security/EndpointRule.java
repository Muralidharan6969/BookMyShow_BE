package com.example.bookmyshow_be.Security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.AntPathMatcher;

import java.util.Objects;

@Getter
public class EndpointRule {
    private final String path;
    private final String method;
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    public EndpointRule(String path, String method) {
        this.path = path;
        this.method = method.toUpperCase();
    }

    public boolean matches(String requestPath, String requestMethod) {
//        String normalizedRulePath = normalizePath(this.path);
//        String normalizedRequestPath = normalizePath(requestPath);

        boolean methodMatches = this.method.equals("*") || this.method.equalsIgnoreCase(requestMethod);
//        boolean pathMatches = normalizedRequestPath.matches(normalizedRulePath.replace("**", ".*").replace("*", "[^/]*"));
        boolean pathMatches = pathMatcher.match(this.path, requestPath);
        return methodMatches && pathMatches;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EndpointRule that = (EndpointRule) o;
        return Objects.equals(path, that.path) && Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, method);
    }

    @Override
    public String toString() {
        return "EndpointRule{" +
                "path='" + path + '\'' +
                ", method='" + method + '\'' +
                '}';
    }

    private String normalizePath(String path) {
        if (path == null || path.equals("/")) return path;
        return path.endsWith("/") ? path.substring(0, path.length() - 1) : path;
    }
}
