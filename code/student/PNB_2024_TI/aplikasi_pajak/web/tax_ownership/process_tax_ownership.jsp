<%-- 
    Document   : process_tax_ownership
    Created on : Sep 17, 2024, 2:22:46?PM
    Author     : ihsan
--%>

<%@page import="com.dimata.entity.pajak.PstTaxType"%>
<%@page import="com.dimata.entity.pajak.PstTaxOwnership"%>
<%@page import="com.dimata.entity.pajak.TaxType"%>
<%@page import="com.dimata.entity.pajak.TaxOwnership"%>
<%@page import="java.util.List"%>
<%@page import="com.dimata.entity.pajak.PaymentStatus"%>
<%
    String noPlat = request.getParameter("noPlat");
    String namaPemilikLama = request.getParameter("namaPemilikLama");
    String namaPemilikBaru = request.getParameter("namaPemilikBaru");
    String alamatBaru = request.getParameter("alamatBaru");
    String namaPajak = request.getParameter("namaPajak");
    double jumlahPajak = Double.parseDouble(request.getParameter("jumlahPajak"));
    String tanggalProses = request.getParameter("tanggalProses");
    String tanggalJatuhTempo = request.getParameter("tanggalJatuhTempo");
    String statusPembayaran = request.getParameter("statusPembayaran");
    String tanggalPembayaran = request.getParameter("tanggalPembayaran");

    // Retrieve the TaxType object based on namaPajak
    TaxType taxType = null;
    try {
        taxType = PstTaxType.fetchByName(namaPajak); // Assuming this method exists to get TaxType by name
    } catch (Exception e) {
        e.printStackTrace();
    }

    // Create and populate the TaxOwnership object
    TaxOwnership taxOwnership = new TaxOwnership();
    taxOwnership.setNoPlat(noPlat);
    taxOwnership.setNamaPemilikLama(namaPemilikLama);
    taxOwnership.setNamaPemilikBaru(namaPemilikBaru);
    taxOwnership.setAlamatBaru(alamatBaru);
    taxOwnership.setTaxType(taxType); // Set the retrieved TaxType
    taxOwnership.setJumlahPajak(jumlahPajak);
    taxOwnership.setTanggalProses(java.sql.Date.valueOf(tanggalProses));
    taxOwnership.setTanggalJatuhTempo(java.sql.Date.valueOf(tanggalJatuhTempo));
    try {
        taxOwnership.setStatusPembayaran(PaymentStatus.valueOf(statusPembayaran));
    } catch (IllegalArgumentException e) {
        e.printStackTrace();
    }
    if (tanggalPembayaran != null && !tanggalPembayaran.isEmpty()) {
        taxOwnership.setTanggalPembayaran(java.sql.Date.valueOf(tanggalPembayaran));
    }

    // Insert the new TaxOwnership record
    try {
        PstTaxOwnership.insertExc(taxOwnership);
    } catch (Exception e) {
        e.printStackTrace();
    }

    // Retrieve the updated list of TaxOwnership records
    List<TaxOwnership> listTypes = null;
    try {
        listTypes = PstTaxOwnership.listAll(0, 0, "", "");
    } catch (Exception e) {
        e.printStackTrace();
    }

    // Output the updated table rows
    out.println("<tbody>");
    if (listTypes == null || listTypes.isEmpty()) {
        out.println("<tr><td colspan='11' class='no-data'>Tidak ada data kepemilikan pajak tersedia.</td></tr>");
    } else {
        int index = 1;
        for (TaxOwnership taxOwnershipItem : listTypes) {
            String taxTypeName = "";
            try {
                TaxType taxTypeObj = taxOwnershipItem.getTaxType();
                if (taxTypeObj != null) {
                    taxTypeName = taxTypeObj.getNamaPajak();
                }
            } catch (Exception e) {
                taxTypeName = "Unknown";
            }
            out.println("<tr>");
            out.println("<td>" + index++ + "</td>");
            out.println("<td>" + taxOwnershipItem.getNoPlat() + "</td>");
            out.println("<td>" + taxOwnershipItem.getNamaPemilikLama() + "</td>");
            out.println("<td>" + taxOwnershipItem.getNamaPemilikBaru() + "</td>");
            out.println("<td>" + taxOwnershipItem.getAlamatBaru() + "</td>");
            out.println("<td>" + taxTypeName + "</td>");
            out.println("<td>" + taxOwnershipItem.getJumlahPajak() + "</td>");
            out.println("<td>" + taxOwnershipItem.getTanggalProses() + "</td>");
            out.println("<td>" + taxOwnershipItem.getTanggalJatuhTempo() + "</td>");
            out.println("<td>" + taxOwnershipItem.getStatusPembayaran() + "</td>");
            out.println("<td>" + (taxOwnershipItem.getTanggalPembayaran() != null ? taxOwnershipItem.getTanggalPembayaran() : "") + "</td>");
            out.println("</tr>");
        }
    }
    out.println("</tbody>");
%>