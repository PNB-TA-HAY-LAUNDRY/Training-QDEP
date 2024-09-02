<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.dimata.entity.pajak.TaxType" %>
<%@ page import="com.dimata.entity.pajak.PstTaxType" %>
<%@ page import="com.dimata.qdep.db.DBException" %>
<!DOCTYPE html>
<html>
<head>
    <title>Daftar Jenis Pajak</title>
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

        /* Konten utama */
        main {
            padding: 20px;
        }

        /* Tabel */
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

        /* Tombol Hapus */
        .delete-btn {
            background-color: #ff6f61;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
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
    </style>
</head>
<body>
    <header>
        <h1>Daftar Jenis Pajak</h1>
    </header>
    <main>
        <%
            // Mengambil parameter deleteId jika ada
            String deleteIdStr = request.getParameter("deleteId");
            if (deleteIdStr != null && !deleteIdStr.isEmpty()) {
                try {
                    long deleteId = Long.parseLong(deleteIdStr);
                    if (deleteId > 0) {
                        int result = PstTaxType.deleteById(deleteId);
                        if (result > 0) {
                            out.println("<p class='success-message'>Data pajak dengan ID " + deleteId + " berhasil dihapus.</p>");
                        } else {
                            out.println("<p class='error-message'>Gagal menghapus data pajak dengan ID " + deleteId + ".</p>");
                        }
                    } else {
                        out.println("<p class='error-message'>ID tidak valid.</p>");
                    }
                } catch (DBException e) {
                    out.println("<p class='error-message'>Gagal menghapus data pajak: " + e.getMessage() + "</p>");
                } catch (NumberFormatException e) {
                    out.println("<p class='error-message'>ID tidak valid.</p>");
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
                            <button type="submit" class="delete-btn">Hapus</button>
                        </form>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </main>
</body>
</html>
