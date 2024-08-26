<%-- 
    Document   : matjenispajak
    Created on : Aug 26, 2024, 11:13:17?PM
    Author     : ihsan
--%>

<%@ page import="java.util.Vector, 
         com.dimata.pajakdaerah.entity.JenisPajak, 
         com.dimata.pajakdaerah.form.FrmPajak, 
         com.dimata.pajakdaerah.form.CtrlPajak" %>
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
            String message = (String) request.getAttribute("message");
            if (message != null) {
                if (message.contains("Perintah tidak valid") || message.contains("Terjadi kesalahan")) {
                    out.println("<div class='message error-message'>" + message + "</div>");
                } else if (message.contains("Berhasil")) {
                    out.println("<div class='message success-message'>" + message + "</div>");
                }
            }

            CtrlPajak controller = new CtrlPajak();
            Vector<JenisPajak> jenisPajakList = controller.getAllJenisPajak();
        %>
        
        <form action="JenisPajakController" method="post">
            <input type="hidden" name="cmd" value="2"> <!-- Command untuk Add -->
            <h2>Tambah Jenis Pajak</h2>
            <label for="nama">Nama:</label>
            <input type="text" id="nama" name="<%= FrmPajak.fieldNames[FrmPajak.FRM_FIELD_NAMA] %>" required>
            <label for="deskripsi">Deskripsi:</label>
            <textarea id="deskripsi" name="<%= FrmPajak.fieldNames[FrmPajak.FRM_FIELD_DESKRIPSI] %>" required></textarea>
            <input type="submit" value="Tambah">
        </form>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nama</th>
                    <th>Deskripsi</th>
                    <th>Aksi</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (JenisPajak jenisPajak : jenisPajakList) {
                %>
                <tr>
                    <td><%= jenisPajak.getOId() %></td>
                    <td><%= jenisPajak.getNama() %></td>
                    <td><%= jenisPajak.getDeskripsi() %></td>
                    <td>
                        <form action="JenisPajakController" method="post" style="display:inline;">
                            <input type="hidden" name="cmd" value="3"> <!-- Command untuk Update -->
                            <input type="hidden" name="<%= FrmPajak.fieldNames[FrmPajak.FRM_FIELD_JENIS_PAJAK_ID] %>" value="<%= jenisPajak.getOId() %>">
                            <input type="hidden" name="<%= FrmPajak.fieldNames[FrmPajak.FRM_FIELD_NAMA] %>" value="<%= jenisPajak.getNama() %>">
                            <input type="hidden" name="<%= FrmPajak.fieldNames[FrmPajak.FRM_FIELD_DESKRIPSI] %>" value="<%= jenisPajak.getDeskripsi() %>">
                            <input type="submit" value="Update">
                        </form>
                        <form action="JenisPajakController" method="post" style="display:inline;">
                            <input type="hidden" name="cmd" value="1"> <!-- Command untuk Delete -->
                            <input type="hidden" name="<%= FrmPajak.fieldNames[FrmPajak.FRM_FIELD_JENIS_PAJAK_ID] %>" value="<%= jenisPajak.getOId() %>">
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