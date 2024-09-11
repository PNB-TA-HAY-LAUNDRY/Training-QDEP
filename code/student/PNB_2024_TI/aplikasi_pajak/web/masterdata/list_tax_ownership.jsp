<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.entity.pajak.PaymentStatus"%>
<%@page import="com.dimata.entity.pajak.TaxType"%>
<%@page import="com.dimata.entity.pajak.TaxOwnership"%>
<%@page import="com.dimata.entity.pajak.PstTaxOwnership"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Daftar Kepemilikan Pajak</title>
    <style>
        :root {
            --main-bg-color: #f4f7f9;
            --card-bg-color: #fff;
            --card-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            --primary-color: #3a5ba0;
            --secondary-color: #426DAE;
            --text-color: #333;
            --text-muted: #666;
            --border-color: #ddd;
        }

        .main-content {
            background-color: var(--card-bg-color);
            padding: 20px;
            border-radius: 8px;
            box-shadow: var(--card-shadow);
            overflow: auto;
            margin: 0 auto; /* Center content within parent */
        }

        .main-content h1 {
            font-size: 24px;
            margin-bottom: 20px;
            color: var(--text-color);
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        table, th, td {
            border: 1px solid var(--border-color);
        }

        th, td {
            padding: 12px;
            text-align: left;
        }

        th {
            background-color: var(--primary-color);
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
            color: var(--text-muted);
            padding: 20px;
            background-color: var(--card-bg-color);
            border: 1px solid var(--border-color);
            border-radius: 8px;
            box-shadow: var(--card-shadow);
        }
    </style>
</head>
<body>
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
                    <th>Tanggal Jatuh Tempo</th>
                    <th>Status Pembayaran</th>
                    <th>Tanggal Pembayaran</th>
                </tr>
            </thead>
            <tbody>
                <%
                    Vector taxOwnershipList = PstTaxOwnership.listAll(0, 0, null, "transfer_tax_id ASC");

                    if (taxOwnershipList != null && taxOwnershipList.size() > 0) {
                        for (int i = 0; i < taxOwnershipList.size(); i++) {
                            TaxOwnership taxOwnership = (TaxOwnership) taxOwnershipList.get(i);
                            String taxTypeName = "";
                            try {
                                TaxType taxType = taxOwnership.getTaxType();
                                if (taxType != null) {
                                    taxTypeName = taxType.getNamaPajak();
                                }
                            } catch (Exception e) {
                                taxTypeName = "Unknown";
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
                    <td><%= taxOwnership.getTanggalJatuhTempo() %></td>
                    <td><%= taxOwnership.getStatusPembayaran() %></td>
                    <td><%= taxOwnership.getTanggalPembayaran() != null ? (taxOwnership.getTanggalPembayaran()) : "" %></td>
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
</body>
</html>