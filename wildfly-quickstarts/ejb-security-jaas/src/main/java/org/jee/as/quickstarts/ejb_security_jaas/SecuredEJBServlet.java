/*
 * JBoss, Home of Professional Open Source
 * Copyright 2017, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jee.as.quickstarts.ejb_security_jaas;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A simple secured Servlet which calls a secured EJB.
 *
 * @author Sherif Makary
 * @author <a href="mailto:sguilhen@redhat.com">Stefan Guilhen</a>
 */
@SuppressWarnings("serial")
@WebServlet("/SecuredEJBServlet")
@ServletSecurity(@HttpConstraint(rolesAllowed = "guest"))
public class SecuredEJBServlet extends HttpServlet {

    private static String PAGE_HEADER = "<html><head><title>ejb-security</title></head><body>";

    private static String PAGE_FOOTER = "</body></html>";

    // Inject the Secured EJB
    @EJB
    private SecuredEJBRemote securedEJB;

    /**
     * Servlet entry point method which calls securedEJB.getSecurityInfo()
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        String principal = null;
        String authType = null;
        String remoteUser = null;

        // Get security principal
        principal = securedEJB.getSecurityInformation();
        // Get user name from login principal
        remoteUser = req.getRemoteUser();
        // Get authentication type
        authType = req.getAuthType();

        boolean hasAdminPermission = false;
        try {
            hasAdminPermission = securedEJB.administrativeMethod();
        } catch (Exception e) {
        }

        writer.println(PAGE_HEADER);
        writer.println("<h1>" + "Successfully called Secured EJB " + "</h1>");
        writer.println("<p>" + "Principal  : " + principal + "</p>");
        writer.println("<p>" + "Remote User : " + remoteUser + "</p>");
        writer.println("<p>" + "Has admin permission : " + hasAdminPermission + "</p>");
        writer.println("<p>" + "Authentication Type : " + authType + "</p>");
        writer.println(PAGE_FOOTER);
        writer.close();
    }
}