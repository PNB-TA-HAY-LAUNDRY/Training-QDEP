<%@ page import="java.io.IOException, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.Vector, com.dimata.entity.pajak.RegionalTax" %>
<%@ page import="com.dimata.entity.pajak.PstRegionalTax" %>

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

    <%
        // Handling deletion of a regional tax entry
        String idParam = request.getParameter("id");
        if (idParam != null) {
            try {
                long id = Long.parseLong(idParam);
                int result = PstRegionalTax.deleteById(id);
                if (result > 0) {
                    out.println("<p>Data pajak daerah dengan ID " + id + " telah dihapus.</p>");
                } else {
                    out.println("<p>Data pajak daerah dengan ID " + id + " tidak ditemukan.</p>");
                }
            } catch (NumberFormatException e) {
                out.println("<p>ID tidak valid.</p>");
            } catch (SQLException e) {
                out.println("<p>Terjadi kesalahan saat menghapus data: " + e.getMessage() + "</p>");
            } catch (Exception e) {
                out.println("<p>Terjadi kesalahan: " + e.getMessage() + "</p>");
            }
        }
    %>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nama Daerah</th>
                <th>Kode Daerah</th>
                <th>Deskripsi</th>
                <th>Aksi</th>
            </tr>
        </thead>
        <tbody>
            <%
                // Fetching list of regional taxes
                Vector<RegionalTax> regionalTaxes = PstRegionalTax.list(0, 100, null, "nama_daerah ASC");
                for (RegionalTax regionalTax : regionalTaxes) {
            %>
            <tr>
                <td><%= regionalTax.getId() %></td>
                <td><%= regionalTax.getName() %></td>
                <td><%= regionalTax.getCode() %></td>
                <td><%= regionalTax.getDescription() %></td>
                <td>
                    <a href="regional_tax_list.jsp?id=<%= regionalTax.getId() %>">Hapus</a>
                </td>
            </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>
