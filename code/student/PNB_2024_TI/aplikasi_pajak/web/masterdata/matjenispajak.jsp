 <%@ page import="java.util.Vector" %>
<%@ page import="com.dimata.pajak.entity.JenisPajak" %>
<%@ page import="com.dimata.pajak.ctrl.JenisPajakController" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manajemen Jenis Pajak</title>
</head>
<body>
    <h1>Manajemen Jenis Pajak</h1>

    <!-- Menampilkan Pesan -->
    <p><%= request.getAttribute("msgString") %></p>

    <!-- Form untuk Menambah atau Memperbarui Jenis Pajak -->
    <form action="JenisPajakController" method="post">
        <input type="hidden" name="cmd" value="<%= request.getAttribute("currentCmd") %>" />
        <input type="hidden" name="id" value="<%= request.getAttribute("currentId") %>" />

        <label for="nama">Nama:</label>
        <input type="text" id="nama" name="nama" value="<%= request.getAttribute("currentNama") %>" required />
        <br />

        <label for="deskripsi">Deskripsi:</label>
        <input type="text" id="deskripsi" name="deskripsi" value="<%= request.getAttribute("currentDeskripsi") %>" required />
        <br />

        <label for="daerah_id">Daerah ID:</label>
        <input type="text" id="daerah_id" name="daerah_id" value="<%= request.getAttribute("currentDaerahId") %>" required />
        <br />

        <input type="submit" value="Simpan" />
    </form>

    <!-- Tabel untuk Menampilkan Daftar Jenis Pajak -->
    <h2>Daftar Jenis Pajak</h2>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nama</th>
                <th>Deskripsi</th>
                <th>Daerah ID</th>
                <th>Aksi</th>
            </tr>
        </thead>
        <tbody>
            <% 
                Vector<JenisPajak> jenisPajakList = (Vector<JenisPajak>) request.getAttribute("jenisPajakList");
                if (jenisPajakList != null) {
                    for (JenisPajak jenisPajak : jenisPajakList) { 
            %>
                <tr>
                    <td><%= jenisPajak.getOId() %></td>
                    <td><%= jenisPajak.getNama() %></td>
                    <td><%= jenisPajak.getDeskripsi() %></td>
                    <td><%= jenisPajak.getDaerahId() %></td>
                    <td>
                        <a href="matjenispajak.jsp?cmd=UPDATE&id=<%= jenisPajak.getOId() %>">Edit</a>
                        <a href="JenisPajakController?cmd=DELETE&id=<%= jenisPajak.getOId() %>">Hapus</a>
                    </td>
                </tr>
            <% 
                    } 
                } else { 
            %>
                <tr>
                    <td colspan="5">Tidak ada data</td>
                </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>
