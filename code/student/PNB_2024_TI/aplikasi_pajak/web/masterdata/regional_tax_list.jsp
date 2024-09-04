<%@ page import="java.io.IOException, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.Vector, com.dimata.entity.pajak.RegionalTax" %>
<%@ page import="com.dimata.entity.pajak.PstRegionalTax" %>

<!DOCTYPE html>
<html>
<head>
    <title>Daftar Pajak Daerah</title>
    <!-- Link ke FontAwesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <!-- Link ke SweetAlert -->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        h1 {
            text-align: center;
            margin-top: 20px;
            color: #333;
        }
        .container {
            width: 80%;
            margin: 0 auto;
            margin-top: 20px;
            background-color: #fff;
            padding: 20px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            border-radius: 5px;
        }
        .actions {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .actions form {
            display: flex;
            align-items: center;
        }
        .actions input[type="text"] {
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-right: 10px;
        }
        .actions button {
            background-color: #007bff;
            border: none;
            color: #fff;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
        }
        .actions button:hover {
            background-color: #0056b3;
        }
        .actions a {
            text-decoration: none;
            color: #fff;
            background-color: #007bff;
            padding: 10px 20px;
            border-radius: 5px;
            display: inline-flex;
            align-items: center;
        }
        .actions a i {
            margin-right: 8px;
        }
        .actions a:hover {
            background-color: #0056b3;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        td a {
            color: #007bff;
            text-decoration: none;
        }
        td a:hover {
            text-decoration: underline;
        }
    </style>
    <script>
        // Fungsi untuk menampilkan konfirmasi SweetAlert sebelum menghapus
        function confirmDelete(id) {
            Swal.fire({
                title: 'Apakah Anda yakin?',
                text: "Data yang dihapus tidak dapat dikembalikan!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Ya, hapus!'
            }).then((result) => {
                if (result.isConfirmed) {
                    // Redirect ke URL penghapusan jika dikonfirmasi
                    window.location.href = 'regional_tax_list.jsp?id=' + id;
                }
            })
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>Daftar Pajak Daerah</h1>

        <!-- Form pencarian -->
        <div class="actions">
            <form method="get" action="regional_tax_list.jsp">
                <input type="text" name="search" placeholder="Cari berdasarkan nama" value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>">
                <button type="submit">Cari</button>
            </form>
            <div>
                <a href="add_regional_tax.jsp"><i class="fas fa-plus"></i> Tambah</a>
                <a href="print_regional_tax.jsp"><i class="fas fa-print"></i> Cetak</a>
            </div>
        </div>

        <!-- Handling deletion of a regional tax entry -->
        <% 
            String idParam = request.getParameter("id");
            if (idParam != null) {
                try {
                    long id = Long.parseLong(idParam);
                    int result = PstRegionalTax.deleteById(id);
                    if (result > 0) {
                        // Redirect to the list page to refresh the view
                        response.sendRedirect("regional_tax_list.jsp");
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
                    String search = request.getParameter("search");
                    Vector<RegionalTax> regionalTaxes;

                    if (search != null && !search.trim().isEmpty()) {
                        String whereClause = "nama_daerah LIKE '%" + search + "%'";
                        // Mengubah batas maksimal hasil pencarian menjadi 100
                        regionalTaxes = PstRegionalTax.list(0, 100, whereClause, "nama_daerah ASC");
                    } else {
                        // Tetap mengambil 100 data jika tidak ada pencarian
                        regionalTaxes = PstRegionalTax.list(0, 100, null, "nama_daerah ASC");
                    }

                    for (RegionalTax regionalTax : regionalTaxes) {
                %>
                <tr>
                    <td><%= regionalTax.getId() %></td>
                    <td><%= regionalTax.getName() %></td>
                    <td><%= regionalTax.getCode() %></td>
                    <td><%= regionalTax.getDescription() %></td>
                    <td>
                        <a href="edit_regional_tax.jsp?id=<%= regionalTax.getId() %>"><i class="fas fa-edit"></i> Edit</a> |
                        <a href="#" onclick="confirmDelete(<%= regionalTax.getId() %>)"><i class="fas fa-trash-alt"></i> Hapus</a>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</body>
</html>