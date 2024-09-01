<%@ page import="java.util.List, com.dimata.entity.pajak.TaxType, com.dimata.entity.pajak.PstTaxType" %>
<!DOCTYPE html>
<html>
<head>
    <title>Daftar Jenis Pajak</title>
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
    <h1>Daftar Jenis Pajak</h1>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nama Pajak</th>
                <th>Deskripsi</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<TaxType> taxTypes = PstTaxType.list(0, 100, null, "nama_pajak ASC");
                for (TaxType taxType : taxTypes) {
            %>
            <tr>
                <td><%= taxType.getId() %></td>
                <td><%= taxType.getName() %></td>
                <td><%= taxType.getDescription() %></td>
            </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>
