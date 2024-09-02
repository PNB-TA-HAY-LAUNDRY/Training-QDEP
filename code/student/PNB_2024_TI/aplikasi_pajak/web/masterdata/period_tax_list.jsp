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
    <title>Daftar Periode Pajak</title>
    <style>
        /* Styling umum */
        body {
            font-family: 'Comic Sans MS', sans-serif;
            background: linear-gradient(45deg, #ff9a9e 0%, #fecfef 99%, #fe99ff 100%);
            color: #333;
            margin: 0;
            padding: 0;
        }

        /* Header */
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

        /* Tabel */
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }

        table, th, td {
            border: 2px solid #ff6f61;
        }

        th, td {
            padding: 12px;
            text-align: left;
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

        /* Tombol Hapus */
        .delete-btn {
            background-color: #ff6f61;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            transition: background-color 0.3s ease;
            text-shadow: 1px 1px #d64f4f;
        }

        .delete-btn:hover {
            background-color: #ff5a4f;
        }

        /* Pesan */
        p {
            font-size: 1.2em;
            font-weight: bold;
            margin: 20px 0;
        }

        .success-message {
            background-color: #d4edda;
            color: #155724;
            border-left: 10px solid #28a745;
            padding: 15px;
            border-radius: 8px;
        }

        .error-message {
            background-color: #f8d7da;
            color: #721c24;
            border-left: 10px solid #dc3545;
            padding: 15px;
            border-radius: 8px;
        }

        /* No Records */
        .no-records {
            text-align: center;
            font-style: italic;
            color: #666;
        }
    </style>
</head>
<body>
    <header>
        <h1>Daftar Periode Pajak</h1>
    </header>
    <main>
        <%
            // Menghapus data periode pajak berdasarkan ID
            String deleteIdStr = request.getParameter("delete");
            if (deleteIdStr != null) {
                try {
                    long deleteId = Long.parseLong(deleteIdStr);
                    PstTaxPeriod.deleteById(deleteId);
                    out.println("<p class='success-message'>Data dengan ID " + deleteId + " telah dihapus.</p>");
                } catch (Exception e) {
                    out.println("<p class='error-message'>Terjadi kesalahan saat menghapus data: " + e.getMessage() + "</p>");
                }
            }
        %>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Tahun</th>
                    <th>Bulan</th>
                    <th>ID Jenis Pajak</th>
                    <th>ID Pajak Regional</th>
                    <th>Aksi</th>
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
                        <a href="tax_period_list.jsp?delete=<%= taxPeriod.getOID() %>" class="delete-btn">Delete</a>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="6" class="no-records">Tidak ada data ditemukan.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </main>
</body>
</html>
