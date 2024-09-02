<%@ page import="java.util.List" %>
<%@ page import="com.dimata.entity.pajak.TaxType" %>
<%@ page import="com.dimata.entity.pajak.PstTaxType" %>
<%@ page import="com.dimata.qdep.db.DBException" %>
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
        .delete-btn {
            color: red;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <h1>Daftar Jenis Pajak</h1>

    <%
    // Mengambil parameter deleteId jika ada
    String deleteIdStr = request.getParameter("deleteId");
    if (deleteIdStr != null && !deleteIdStr.isEmpty()) {
        try {
            long deleteId = Long.parseLong(deleteIdStr);
            if (deleteId > 0) {
                int result = PstTaxType.deleteById(deleteId);
                if (result > 0) {
                    out.println("<p style='color: green;'>Data pajak dengan ID " + deleteId + " berhasil dihapus.</p>");
                } else {
                    out.println("<p style='color: red;'>Gagal menghapus data pajak dengan ID " + deleteId + ".</p>");
                }
            } else {
                out.println("<p style='color: red;'>ID tidak valid.</p>");
            }
        } catch (DBException e) {
            out.println("<p style='color: red;'>Gagal menghapus data pajak: " + e.getMessage() + "</p>");
        } catch (NumberFormatException e) {
            out.println("<p style='color: red;'>ID tidak valid.</p>");
        }
    }

    // Menampilkan daftar jenis pajak
    List<TaxType> taxTypes = PstTaxType.list(0, 100, null, "id_jenis_pajak ASC");
%>

<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Nama Pajak</th>
            <th>Deskripsi</th>
            <th>Aksi</th>
        </tr>
    </thead>
    <tbody>
        <%
            for (TaxType taxType : taxTypes) {
        %>
        <tr>
            <td><%= taxType.getId() %></td>
            <td><%= taxType.getName() %></td>
            <td><%= taxType.getDescription() %></td>
            <td>
                <!-- Tombol Delete dengan parameter ID -->
                <form method="post" action="">
                    <input type="hidden" name="deleteId" value="<%= taxType.getId() %>">
                    <button type="submit" class="delete-btn">Delete</button>
                </form>
            </td>
        </tr>
        <% } %>
    </tbody>
</table>
</body>
</html>
