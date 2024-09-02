<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.dimata.entity.pajak.PstTaxBill" %>
<%@ page import="com.dimata.entity.pajak.TaxBill" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tax Bill List</title>
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 0;
            }
            .container {
                width: 80%;
                margin: auto;
                overflow: hidden;
                padding: 20px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 20px;
            }
            th, td {
                padding: 10px;
                border: 1px solid #ddd;
                text-align: left;
            }
            th {
                background-color: #2c3e50;
                color: white;
            }
            tr:nth-child(even) {
                background-color: #f2f2f2;
            }
            .btn {
                display: inline-block;
                padding: 5px 10px;
                color: white;
                background-color: #e74c3c;
                text-decoration: none;
                border-radius: 3px;
                font-size: 14px;
            }
            .btn:hover {
                background-color: #c0392b;
            }
            .no-records {
                text-align: center;
                font-style: italic;
            }
            .btn-container {
                text-align: right;
                margin-top: 20px;
            }
            .btn-container .btn {
                background-color: #3498db;
                color: white;
                padding: 10px 20px;
            }
            .btn-container .btn:hover {
                background-color: #2980b9;
            }
        </style>
    </head>
    <body>
        <%
            // Handle delete action
            String action = request.getParameter("action");
            if ("delete".equals(action)) {
                try {
                    long id = Long.parseLong(request.getParameter("id"));
                    int result = PstTaxBill.deleteById(id);
                    if (result > 0) {
                        out.println("<p>Record deleted successfully.</p>");
                    } else {
                        out.println("<p>Record not found.</p>");
                    }
                } catch (NumberFormatException e) {
                    out.println("<p>Invalid ID format.</p>");
                } catch (SQLException e) {
                    out.println("<p>Error deleting record: " + e.getMessage() + "</p>");
                } catch (Exception e) {
                    out.println("<p>Error: " + e.getMessage() + "</p>");
                }
            }
        %>
        <div class="container">
            <h1>Tax Bill List</h1>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Amount</th>
                        <th>Tax Type ID</th>
                        <th>Regional Tax ID</th>
                        <th>Tax Period ID</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        int limitStart = 0;
                        int recordToGet = 10;
                        String whereClause = "";
                        String order = "id_tagihan_pajak DESC";

                        Vector<TaxBill> taxBillList = PstTaxBill.list(limitStart, recordToGet, whereClause, order);

                        if (taxBillList != null && !taxBillList.isEmpty()) {
                            for (int i = 0; i < taxBillList.size(); i++) {
                                TaxBill taxBill = taxBillList.get(i);
                    %>
                    <tr>
                        <td><%= taxBill.getOID() %></td>
                        <td><%= taxBill.getAmount() %></td>
                        <td><%= taxBill.getTaxTypeId() %></td>
                        <td><%= taxBill.getRegionalTaxId() %></td>
                        <td><%= taxBill.getTaxPeriodId() %></td>
                        <td>
                            <a href="bill_tax_list.jsp?action=delete&id=<%= taxBill.getOID() %>" class="btn">Delete</a>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="6" class="no-records">No records found.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
            <div class="btn-container">
                <a href="add_tax_bill.jsp" class="btn">Add New Tax Bill</a>
            </div>
        </div>
    </body>
</html>
