<%@ page import="java.io.IOException, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.Vector, com.dimata.entity.pajak.RegionalTax" %>
<%@ page import="com.dimata.entity.pajak.PstRegionalTax" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Daftar Pajak Daerah</title>
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

        /* Link Hapus */
        a {
            color: #ff6f61;
            text-decoration: none;
            font-weight: bold;
        }

        a:hover {
            color: #ff5a4f;
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
    </style>
</head>
<body>
    <header>
        <h1>Daftar Pajak Daerah</h1>
    </header>
    <main>
        <%
            // Menangani penghapusan data pajak daerah berdasarkan ID
            String idParam = request.getParameter("id");
            if (idParam != null) {
                try {
                    long id = Long.parseLong(idParam);
                    int result = PstRegionalTax.deleteById(id);
                    if (result > 0) {
                        out.println("<p class='success-message'>Data pajak daerah dengan ID " + id + " telah dihapus.</p>");
                    } else {
                        out.println("<p class='error-message'>Data pajak daerah dengan ID " + id + " tidak ditemukan.</p>");
                    }
                } catch (NumberFormatException e) {
                    out.println("<p class='error-message'>ID tidak valid.</p>");
                } catch (SQLException e) {
                    out.println("<p class='error-message'>Terjadi kesalahan saat menghapus data: " + e.getMessage() + "</p>");
                } catch (Exception e) {
                    out.println("<p class='error-message'>Terjadi kesalahan: " + e.getMessage() + "</p>");
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
                    // Mengambil daftar pajak daerah
                    Vector<RegionalTax> regionalTaxes = PstRegionalTax.list(0, 100, null, "nama_daerah ASC");
                    if (regionalTaxes != null && !regionalTaxes.isEmpty()) {
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
                <% 
                        }
                    } else {
                %>
                <tr>
                    <td colspan="5" class="no-records">Tidak ada data ditemukan.</td>
                </tr>
                <% 
                    }
                %>
            </tbody>
        </table>
    </main>
</body>
</html>
