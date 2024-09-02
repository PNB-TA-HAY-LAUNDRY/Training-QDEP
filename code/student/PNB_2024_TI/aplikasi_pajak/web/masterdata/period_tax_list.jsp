<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.dimata.entity.pajak.PstTaxPeriod" %>
<%@ page import="com.dimata.entity.pajak.TaxPeriod" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tax Period List</title>
        <style>
            table {
                width: 100%;
                border-collapse: collapse;
            }
            table, th, td {
                border: 1px solid black;
            }
            th, td {
                padding: 8px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
        </style>
    </head>
    <body>
        <h1>Tax Period List</h1>

        <%
            // Handle delete action
            String deleteIdStr = request.getParameter("delete");
            if (deleteIdStr != null) {
                try {
                    long deleteId = Long.parseLong(deleteIdStr);
                    PstTaxPeriod.deleteById(deleteId);
                    out.println("<p>Data with ID " + deleteId + " has been deleted.</p>");
                } catch (Exception e) {
                    out.println("<p>Error occurred while deleting data: " + e.getMessage() + "</p>");
                }
            }
        %>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Year</th>
                    <th>Month</th>
                    <th>Tax Type ID</th>
                    <th>Regional Tax ID</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <%
                    int limitStart = 0;
                    int recordToGet = 10;
                    String whereClause = "";
                    String order = "id_periode_pajak DESC";

                    Vector<TaxPeriod> taxPeriodList = PstTaxPeriod.list(limitStart, recordToGet, whereClause, order);

                    if (taxPeriodList != null && !taxPeriodList.isEmpty()) {
                        for (int i = 0; i < taxPeriodList.size(); i++) {
                            TaxPeriod taxPeriod = taxPeriodList.get(i);
                %>
                <tr>
                    <td><%= taxPeriod.getOID() %></td>
                    <td><%= taxPeriod.getYear() %></td>
                    <td><%= taxPeriod.getMonth() %></td>
                    <td><%= taxPeriod.getTaxTypeId() %></td>
                    <td><%= taxPeriod.getRegionalTaxId() %></td>
                    <td>
                        <form action="" method="get" style="display:inline;">
                            <input type="hidden" name="delete" value="<%= taxPeriod.getOID() %>">
                            <input type="submit" value="Delete">
                        </form>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="6">No records found.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </body>
</html>
