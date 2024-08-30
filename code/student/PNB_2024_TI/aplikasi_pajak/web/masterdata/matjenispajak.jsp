<%@ page import="java.util.Vector, com.dimata.pajak.entity.JenisPajak, com.dimata.pajak.entity.Daerah, com.dimata.pajak.ctrl.FrmJenisPajak, com.dimata.pajak.ctrl.JenisPajakController, com.dimata.pajak.ctrl.DaerahController" %>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Daftar Jenis Pajak</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/styles/styles.css">
</head>
<body>
    <header>
        <h1>Daftar Jenis Pajak</h1>
    </header>
    <main>
        <%
            // Mengambil pesan dari request
            String message = (String) request.getAttribute("message");
            if (message != null) {
                String messageClass = message.contains("Perintah tidak valid") || message.contains("Terjadi kesalahan") ? "error-message" : "success-message";
                out.println("<div class='message " + messageClass + "'>" + message + "</div>");
            }

            // Mendapatkan daftar jenis pajak
            JenisPajakController jenisPajakController = new JenisPajakController();
            Vector<JenisPajak> jenisPajakList = jenisPajakController.getAllJenisPajak();

            // Mendapatkan daftar daerah
            DaerahController daerahController = new DaerahController();
            Vector<Daerah> daerahList = daerahController.getAllDaerah();
        %>
        
        <!-- Form untuk menambahkan jenis pajak -->
        <form action="JenisPajakController" method="post">
            <input type="hidden" name="cmd" value="2"> <!-- Command untuk Add -->
            <h2>Tambah Jenis Pajak</h2>
            <label for="nama">Nama:</label>
            <input type="text" id="nama" name="<%= FrmJenisPajak.fieldNames[FrmJenisPajak.FRM_FIELD_NAMA] %>" required>
            <label for="deskripsi">Deskripsi:</label>
            <textarea id="deskripsi" name="<%= FrmJenisPajak.fieldNames[FrmJenisPajak.FRM_FIELD_DESKRIPSI] %>" required></textarea>
            <label for="daerah">Daerah:</label>
            <select id="daerah" name="<%= FrmJenisPajak.fieldNames[FrmJenisPajak.FRM_FIELD_DAERAH_ID] %>" required>
                <%
                    for (Daerah daerah : daerahList) {
                        out.println("<option value='" + daerah.getOId() + "'>" + daerah.getNama() + "</option>");
                    }
                %>
            </select>
            <input type="submit" value="Tambah">
        </form>

        <!-- Tabel untuk menampilkan jenis pajak -->
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nama</th>
                    <th>Deskripsi</th>
                    <th>Daerah</th>
                    <th>Aksi</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (JenisPajak jenisPajak : jenisPajakList) {
                        Daerah daerah = daerahController.getDaerahById(jenisPajak.getDaerahId()); // Mendapatkan daerah terkait
                %>
                <tr>
                    <td><%= jenisPajak.getOId() %></td>
                    <td><%= jenisPajak.getNama() %></td>
                    <td><%= jenisPajak.getDeskripsi() %></td>
                    <td><%= daerah != null ? daerah.getNama() : "N/A" %></td>
                    <td>
                        <!-- Formulir untuk Update -->
                        <form action="JenisPajakController" method="post" style="display:inline;">
                            <input type="hidden" name="cmd" value="3"> <!-- Command untuk Update -->
                            <input type="hidden" name="<%= FrmJenisPajak.fieldNames[FrmJenisPajak.FRM_FIELD_JENIS_PAJAK_ID] %>" value="<%= jenisPajak.getOId() %>">
                            <input type="submit" value="Update">
                        </form>
                        <!-- Formulir untuk Delete -->
                        <form action="JenisPajakController" method="post" style="display:inline;">
                            <input type="hidden" name="cmd" value="1"> <!-- Command untuk Delete -->
                            <input type="hidden" name="<%= FrmJenisPajak.fieldNames[FrmJenisPajak.FRM_FIELD_JENIS_PAJAK_ID] %>" value="<%= jenisPajak.getOId() %>">
                            <input type="submit" value="Delete" onclick="return confirm('Apakah Anda yakin ingin menghapus data ini?');">
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
