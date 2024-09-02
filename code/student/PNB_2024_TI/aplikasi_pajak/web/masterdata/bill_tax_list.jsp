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
                font-family: 'Comic Sans MS', sans-serif;
                background: linear-gradient(45deg, #ff9a9e 0%, #fecfef 99%, #fe99ff 100%);
                color: #333;
                margin: 0;
                padding: 0;
            }

            header {
                background-color: #ff6f61;
                color: white;
                padding: 20px;
                text-align: center;
                box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
            }

            h1 {
                margin: 0;
                font-size: 2.5em;
                text-transform: uppercase;
                letter-spacing: 2px;
                text-shadow: 2px 2px #d64f4f;
            }

            main {
                padding: 20px;
            }

            form {
                background-color: #fff3e0;
                padding: 20px;
                border-radius: 10px;
                margin-bottom: 30px;
                box-shadow: 0px 6px 12px rgba(0, 0, 0, 0.1);
                border-left: 10px solid #ff6f61;
            }

            form h2 {
                margin-top: 0;
                color: #ff6f61;
                font-size: 1.8em;
                text-shadow: 1px 1px #d64f4f;
            }

            form label {
                display: block;
                margin: 10px 0 5px;
                font-weight: bold;
                color: #333;
            }

            form input[type="text"],
            form textarea {
                width: calc(100% - 20px);
                padding: 10px;
                border: 2px solid #ff6f61;
                border-radius: 5px;
                font-size: 1em;
                box-shadow: inset 0px 2px 5px rgba(0, 0, 0, 0.1);
            }

            form input[type="submit"] {
                background-color: #ff6f61;
                color: white;
                border: none;
                padding: 10px 20px;
                margin-top: 15px;
                border-radius: 5px;
                font-size: 1.2em;
                cursor: pointer;
                transition: background-color 0.3s ease;
                text-shadow: 1px 1px #d64f4f;
            }

            form input[type="submit"]:hover {
                background-color: #ff5a4f;
            }

            .message {
                padding: 15px;
                margin: 15px 0;
                border-radius: 8px;
                font-size: 1.2em;
                border-left: 10px solid;
            }

            .success-message {
                background-color: #d4edda;
                color: #155724;
                border-left-color: #28a745;
            }

            .error-message {
                background-color: #f8d7da;
                color: #721c24;
                border-left-color: #dc3545;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 30px;
            }

            table, th, td {
                border: 2px solid #ff6f61;
            }

            th, td {
                padding: 15px;
                text-align: center;
                background-color: #ffede0;
                color: #333;
                font-size: 1.1em;
            }

            th {
                background-color: #ff6f61;
                color: white;
                font-size: 1.3em;
                text-shadow: 1px 1px #d64f4f;
            }

            tr:nth-child(even) {
                background-color: #ffe8d1;
            }

            tr:hover {
                background-color: #ffd1a4;
            }

            .btn {
                display: inline-block;
                padding: 5px 10px;
                color: white;
                background-color: #e74c3c;
                text-decoration: none;
                border-radius: 3px;
                font-size: 14px;
                transition: background-color 0.3s ease;
            }

            .btn:hover {
                background-color: #c0392b;
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

            .no-records {
                text-align: center;
                font-style: italic;
            }
        </style>
    </head>
    <body>
        <header>
            <h1>Tax Bill List</h1>
        </header>
        <main>
            <%
                // Handle delete action
                String action = request.getParameter("action");
                if ("delete".equals(action)) {
                    try {
                        long id = Long.parseLong(request.getParameter("id"));
                        int result = PstTaxBill.deleteById(id);
                        if (result > 0) {
                            out.println("<div class='message success-message'>Record deleted successfully.</div>");
                        } else {
                            out.println("<div class='message error-message'>Record not found.</div>");
                        }
                    } catch (NumberFormatException e) {
                        out.println("<div class='message error-message'>Invalid ID format.</div>");
                    } catch (SQLException e) {
                        out.println("<div class='message error-message'>Error deleting record: " + e.getMessage() + "</div>");
                    } catch (Exception e) {
                        out.println("<div class='message error-message'>Error: " + e.getMessage() + "</div>");
                    }
                }
            %>
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
        </main>
    </body>
</html>
