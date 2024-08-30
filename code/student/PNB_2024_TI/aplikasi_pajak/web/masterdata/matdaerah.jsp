<%@ page import="java.util.Vector, com.dimata.pajak.entity.Daerah, com.dimata.pajak.entity.PstDaerah, com.dimata.pajak.ctrl.DaerahController" %>

<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Daftar Daerah</title>
    <link rel="stylesheet" type="text/css" href="/style/styles.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }

        header {
            background-color: #333;
            color: #fff;
            padding: 1rem;
            text-align: center;
        }

        main {
            padding: 2rem;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 1.5rem;
            background-color: #fff;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        th, td {
            padding: 0.75rem;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #f2f2f2;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        form {
            display: inline;
        }

        .action-btn {
            background-color: #4CAF50; /* Green */
            color: white;
            border: none;
            padding: 0.5rem 1rem;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 0.875rem;
            margin: 0 0.25rem;
            cursor: pointer;
            border-radius: 4px;
        }

        .delete-btn {
            background-color: #f44336; /* Red */
        }

        .confirm-box {
            display: none;
        }

        .confirm-box.active {
            display: block;
        }
    </style>
</head>
<body>
    <header>
        <h1>Daftar Daerah</h1>
    </header>
    <main>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nama</th>
                    <th>Kode Daerah</th>
                    <th>Deskripsi</th>
                    <th>Aksi</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Mengambil data daerah dari database
                    Vector<Daerah> daerahList = PstDaerah.list(0, Integer.MAX_VALUE, null, null);
                    for (Daerah daerah : daerahList) {
                %>
                <tr>
                    <td><%= daerah.getOId() %></td>
                    <td><%= daerah.getNama() %></td>
                    <td><%= daerah.getKodeDaerah() %></td>
                    <td><%= daerah.getDeskripsi() %></td>
                    <td>
                        <form action="DaerahController" method="post" style="display:inline;">
                            <input type="hidden" name="cmd" value="3"> <!-- Command untuk Update -->
                            <input type="hidden" name="id" value="<%= daerah.getOId() %>">
                            <input type="submit" class="action-btn" value="Update">
                        </form>
                        <form action="DaerahController" method="post" style="display:inline;">
                            <input type="hidden" name="cmd" value="1"> <!-- Command untuk Delete -->
                            <input type="hidden" name="id" value="<%= daerah.getOId() %>">
                            <input type="submit" class="action-btn delete-btn" value="Delete" onclick="return confirm('Apakah Anda yakin ingin menghapus data ini?');">
                        </form>
                    </td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </main>
</body>
</html>
