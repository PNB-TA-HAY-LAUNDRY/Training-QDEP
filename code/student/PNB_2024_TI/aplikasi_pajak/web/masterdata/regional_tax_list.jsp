<%@ page import="java.util.Vector, com.dimata.entity.pajak.RegionalTax, com.dimata.entity.pajak.PstRegionalTax" %>
<!DOCTYPE html>
<html>
<head>
    <title>Daftar Pajak Daerah</title>
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
    <h1>Daftar Pajak Daerah</h1>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nama Daerah</th>
                <th>Kode Daerah</th>
                <th>Deskripsi</th>
            </tr>
        </thead>
        <tbody>
            <%
                Vector<RegionalTax> regionalTaxes = PstRegionalTax.list(0, 100, null, "nama_daerah ASC");
                for (RegionalTax regionalTax : regionalTaxes) {
            %>
            <tr>
                <td><%= regionalTax.getId() %></td>
                <td><%= regionalTax.getName() %></td>
                <td><%= regionalTax.getCode() %></td>
                <td><%= regionalTax.getDescription() %></td>
            </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>
