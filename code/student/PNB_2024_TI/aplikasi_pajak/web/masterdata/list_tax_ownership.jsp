<%-- 
    Document   : list_tax_ownership
    Created on : Sep 10, 2024, 2:09:41 PM
    Author     : ihsan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.entity.pajak.TaxType"%>
<%@page import="com.dimata.entity.pajak.TaxOwnership"%>
<%@page import="com.dimata.entity.pajak.PstTaxOwnership"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Daftar Kepemilikan Pajak</title>
    <style>
        /* Ensuring styles are consistent with the index design */
        html, body {
            height: 100%;
            margin: 0;
            font-family: 'Roboto', sans-serif;
            background-color: #f4f7f9;
        }
        .container {
            display: flex;
            height: 100vh;
        }
        .main-content-wrapper {
            flex-grow: 1;
            display: flex;
            overflow: hidden;
        }
        .main-content {
            flex: 1;
            background-color: #fff;
            padding: 30px;
            margin: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            overflow: auto;
            transition: opacity 0.5s ease-in-out, transform 0.5s ease-in-out;
            opacity: 1;
            transform: translateX(0);
        }
        .main-content h1 {
            font-size: 24px;
            margin-bottom: 20px;
            color: #333;
            font-weight: 400;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #3a5ba0;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .no-data {
            text-align: center;
            color: #666;
            padding: 20px;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>

    <div class="main-content-wrapper">
        <div class="main-content">
            <h1>Daftar Kepemilikan Pajak</h1>
            <table>
                <thead>
                    <tr>
                        <th>No</th>
                        <th>No Plat</th>
                        <th>Nama Pemilik Lama</th>
                        <th>Nama Pemilik Baru</th>
                        <th>Alamat Baru</th>
                        <th>Jenis Pajak</th>
                        <th>Jumlah Pajak</th>
                        <th>Tanggal Proses</th>
                        <th>Status Pembayaran</th>
                        <th>Tanggal Jatuh Tempo</th>
                        <th>Tanggal Pembayaran</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        // Mendapatkan data dari database menggunakan PstTaxOwnership
                        Vector taxOwnershipList = PstTaxOwnership.listAll(0, 0, null, "transfer_tax_id ASC");

                        if (taxOwnershipList != null && taxOwnershipList.size() > 0) {
                            // Iterasi melalui daftar kepemilikan pajak
                            for (int i = 0; i < taxOwnershipList.size(); i++) {
                                TaxOwnership taxOwnership = (TaxOwnership) taxOwnershipList.get(i);
                                String taxTypeName = ""; // Default value
                                try {
                                    TaxType taxType = taxOwnership.getTaxType();
                                    if (taxType != null) {
                                        taxTypeName = taxType.getNamaPajak();
                                    }
                                } catch (Exception e) {
                                    // Handle exception or set a default value if needed
                                }
                    %>
                    <tr>
                        <td><%= taxOwnership.getTransferTaxId() %></td>
                        <td><%= taxOwnership.getNoPlat() %></td>
                        <td><%= taxOwnership.getNamaPemilikLama() %></td>
                        <td><%= taxOwnership.getNamaPemilikBaru() %></td>
                        <td><%= taxOwnership.getAlamatBaru() %></td>
                        <td><%= taxTypeName %></td>
                        <td><%= taxOwnership.getJumlahPajak() %></td>
                        <td><%= taxOwnership.getTanggalProses() %></td>
                        <td><%= taxOwnership.getStatusPembayaran() %></td>
                        <td><%= taxOwnership.getTanggalJatuhTempo() %></td>
                        <td><%= taxOwnership.getTanggalPembayaran() %></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="11" class="no-data">Tidak ada data ditemukan</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </div>

</body>
</html>
