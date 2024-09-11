<%@page import="com.dimata.entity.pajak.TaxType"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.entity.pajak.TaxVehicle"%>
<%@page import="com.dimata.entity.pajak.PstTaxVehicle"%>
<%@page import="com.dimata.entity.pajak.PaymentStatus"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Daftar Pajak Kendaraan</title>
    <style>
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

    <div class="container">
        <div class="content">
            <h1>Daftar Pajak Kendaraan</h1>
            <table>
                <thead>
                    <tr>
                        <th>No</th>
                        <th>No Plat</th>
                        <th>Nama Pemilik</th>
                        <th>Alamat</th>
                        <th>Merk</th>
                        <th>Model</th>
                        <th>Tahun Pembuatan</th>
                        <th>Jenis Pajak</th>
                        <th>Jumlah Pajak</th>
                        <th>Periode Pajak</th>
                        <th>Tanggal Jatuh Tempo</th>
                        <th>Status Pembayaran</th>
                        <th>Tanggal Pembayaran</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        // Mendapatkan data dari database menggunakan PstTaxVehicle
                        Vector taxVehicleList = PstTaxVehicle.listAll(0, 0, null, "vehicle_tax_id ASC");

                        if (taxVehicleList != null && taxVehicleList.size() > 0) {
                            // Iterasi melalui daftar pajak kendaraan
                            for (int i = 0; i < taxVehicleList.size(); i++) {
                                TaxVehicle taxVehicle = (TaxVehicle) taxVehicleList.get(i);
                                String taxTypeName = ""; // Default value
                                try {
                                    TaxType taxType = taxVehicle.getTaxType();
                                    if (taxType != null) {
                                        taxTypeName = taxType.getNamaPajak();
                                    }
                                } catch (Exception e) {
                                    taxTypeName = "Unknown";
                                }
                    %>
                    <tr>
                        <td><%= i + 1 %></td>
                        <td><%= taxVehicle.getNoPlat() %></td>
                        <td><%= taxVehicle.getNamaPemilik() %></td>
                        <td><%= taxVehicle.getAlamat() %></td>
                        <td><%= taxVehicle.getMerk() %></td>
                        <td><%= taxVehicle.getModel() %></td>
                        <td><%= taxVehicle.getTahunPembuatan() %></td>
                        <td><%= taxTypeName %></td>
                        <td><%= taxVehicle.getJumlahPajak() %></td>
                        <td><%= taxVehicle.getPeriodePajak() %></td>
                        <td><%= taxVehicle.getTanggalJatuhTempo()%></td>
                        <td><%= taxVehicle.getStatusPembayaran() %></td>
                        <td><%= taxVehicle.getTanggalPembayaran() %></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="13" class="no-data">Data tidak tersedia</td>
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
