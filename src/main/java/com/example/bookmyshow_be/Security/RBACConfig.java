package com.example.bookmyshow_be.Security;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RBACConfig {
    private final Map<String, List<EndpointRule>> roleToRules;

    public RBACConfig() {
        roleToRules = new HashMap<>();
        roleToRules.put("ADMIN", Arrays.asList(
                new EndpointRule("/cities/**", "*"),
                new EndpointRule("/admin/**", "*"),
                new EndpointRule("/movies/**", "*"),
                new EndpointRule("/theatres/**", "GET"),
                new EndpointRule("/theatres/**", "PATCH")
        ));
        roleToRules.put("OUTLET", Arrays.asList(
                new EndpointRule("/outlets/**", "*"),
                new EndpointRule("/bookings/**", "*"),
                new EndpointRule("/cities/**", "GET"),
                new EndpointRule("/movies/**", "GET"),
                new EndpointRule("/theatres/**", "POST"),
                new EndpointRule("/theatres/screens/**", "GET")
        ));
        roleToRules.put("USER", Arrays.asList(
                new EndpointRule("/users/**", "*"),
                new EndpointRule("/movies/**", "GET"),
                new EndpointRule("/bookings/**", "*"),
                new EndpointRule("/cities/**", "GET"),
                new EndpointRule("/theatres/**", "GET"),
                new EndpointRule("/shows/**", "GET"),
                new EndpointRule("/theatres/shows/{showId}/block-seats", "POST"),
                new EndpointRule("/bookings/**", "*"),
                new EndpointRule("/stripe-booking/**", "POST")
        ));
    }

    public List<EndpointRule> getAllowedRules(String userType) {
        return roleToRules.getOrDefault(userType, Collections.emptyList());
    }

    public boolean hasAccess(String userType, String path, String method) {
        List<EndpointRule> allowedRules = getAllowedRules(userType);
        System.out.println("Request Path: " + path);
        System.out.println("Request Method: " + method);
        System.out.println("User Type: " + userType);
        System.out.println("Allowed Rules: " + allowedRules);

        for (EndpointRule rule : allowedRules) {
            System.out.println("Checking rule: " + rule);
            if (rule.matches(path, method)) {
                System.out.println("Matched rule: " + rule);
                return true;
            }
        }
        return false;
    }
}
