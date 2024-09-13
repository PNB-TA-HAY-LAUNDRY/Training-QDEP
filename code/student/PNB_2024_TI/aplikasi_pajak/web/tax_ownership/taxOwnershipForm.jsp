<%-- 
    Document   : taxOwnershipForm
    Created on : Sep 13, 2024, 8:59:19 AM
    Author     : ihsan
--%>

<%@page import="com.dimata.util.Command"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.dimata.form.pajak.CtrlTaxOwnership"%>
<%@page import="com.dimata.form.pajak.FrmTaxOwnership"%>
<%@page import="com.dimata.entity.pajak.TaxOwnership"%>
<%@page import="javax.servlet.http.HttpServletRequest"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
    // Inisialisasi controller dan form handler
    CtrlTaxOwnership ctrlTaxOwnership = new CtrlTaxOwnership(request);
    FrmTaxOwnership frmTaxOwnership = ctrlTaxOwnership.getForm();
    TaxOwnership taxOwnership = ctrlTaxOwnership.getTaxOwnership();
    String message = ctrlTaxOwnership.getMessage();

    // Jika ada aksi SAVE, panggil controller untuk menyimpan data
    int action = request.getParameter("cmd") != null ? Integer.parseInt(request.getParameter("cmd")) : 0;
    if (action == Command.SAVE) {
        long oidTax = 0;
        String oidTaxStr = request.getParameter("oidTax");
        if (oidTaxStr != null && !oidTaxStr.isEmpty()) {
            oidTax = Long.parseLong(oidTaxStr);
        }
        int result = ctrlTaxOwnership.action(Command.SAVE, oidTax);
        if (result == CtrlTaxOwnership.RSLT_OK) {
            response.sendRedirect("list_tax_ownership.jsp"); // Redirect ke halaman daftar jika berhasil
        }
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Form Tax Ownership</title>
    </head>
    <body>
        <h1>Tambah Data Pajak Kendaraan</h1>

        <% if (message != null && !message.isEmpty()) {%>
        <div style="color: red;">
            <%= message%>
        </div>
        <% }%>

        <form action="taxOwnershipForm.jsp" method="post">
            <input type="hidden" name="cmd" value="<%= Command.SAVE%>">
            <input type="hidden" name="oidTax" value="<%= taxOwnership.getOID()%>">

            <label>No Plat:</label>
            <input type="text" name="<%= FrmTaxOwnership.fieldNames[FrmTaxOwnership.FRM_FIELD_NO_PLAT]%>" 
                   value="<%= taxOwnership.getNoPlat() != null ? taxOwnership.getNoPlat() : ""%>"><br><br>

            <label>Nama Pemilik Lama:</label>
            <input type="text" name="<%= FrmTaxOwnership.fieldNames[FrmTaxOwnership.FRM_FIELD_OLD_NAME]%>" 
                   value="<%= taxOwnership.getNamaPemilikLama() != null ? taxOwnership.getNamaPemilikLama() : ""%>"><br><br>

            <label>Nama Pemilik Baru:</label>
            <input type="text" name="<%= FrmTaxOwnership.fieldNames[FrmTaxOwnership.FRM_FIELD_NEW_NAME]%>" 
                   value="<%= taxOwnership.getNamaPemilikBaru() != null ? taxOwnership.getNamaPemilikBaru() : ""%>"><br><br>

            <label>Alamat Baru:</label>
            <input type="text" name="<%= FrmTaxOwnership.fieldNames[FrmTaxOwnership.FRM_FIELD_NEW_ADDRESS]%>" 
                   value="<%= taxOwnership.getAlamatBaru() != null ? taxOwnership.getAlamatBaru() : ""%>"><br><br>

            <label>Jenis Pajak (ID):</label>
            <input type="text" name="<%= FrmTaxOwnership.fieldNames[FrmTaxOwnership.FRM_FIELD_TAX_TYPE_ID]%>" 
                   value="<%= taxOwnership.getTaxType() != null ? taxOwnership.getTaxType().getTaxTypeId() : ""%>"><br><br>

            <label>Jumlah Pajak:</label>
            <input type="text" name="<%= FrmTaxOwnership.fieldNames[FrmTaxOwnership.FRM_FIELD_TOTAL_TAX]%>" 
                   value="<%= taxOwnership.getJumlahPajak() != 0 ? taxOwnership.getJumlahPajak() : ""%>"><br><br>

            <label>Tanggal Proses:</label>
            <input type="date" name="<%= FrmTaxOwnership.fieldNames[FrmTaxOwnership.FRM_FIELD_PROCESS_DATE]%>" 
                   value="<%= taxOwnership.getTanggalProses() != null ? new SimpleDateFormat("yyyy-MM-dd").format(taxOwnership.getTanggalProses()) : ""%>"><br><br>

            <label>Status Pembayaran:</label>
            <select name="<%= FrmTaxOwnership.fieldNames[FrmTaxOwnership.FRM_FIELD_PAY_STATUS]%>">
                <option value="UNPAID" <%= "UNPAID".equals(taxOwnership.getStatusPembayaran().toString()) ? "selected" : ""%>>Belum Dibayar</option>
                <option value="PAID" <%= "PAID".equals(taxOwnership.getStatusPembayaran().toString()) ? "selected" : ""%>>Dibayar</option>
            </select><br><br>

            <label>Tanggal Jatuh Tempo:</label>
            <input type="date" name="<%= FrmTaxOwnership.fieldNames[FrmTaxOwnership.FRM_FIELD_DUE_DATE]%>" 
                   value="<%= taxOwnership.getTanggalJatuhTempo() != null ? new SimpleDateFormat("yyyy-MM-dd").format(taxOwnership.getTanggalJatuhTempo()) : ""%>"><br><br>

            <label>Tanggal Pembayaran:</label>
            <input type="date" name="<%= FrmTaxOwnership.fieldNames[FrmTaxOwnership.FRM_FIELD_PAY_DATE]%>" 
                   value="<%= taxOwnership.getTanggalPembayaran() != null ? new SimpleDateFormat("yyyy-MM-dd").format(taxOwnership.getTanggalPembayaran()) : ""%>"><br><br>

            <button type="submit">Simpan</button>
        </form>
    </body>
</html>
