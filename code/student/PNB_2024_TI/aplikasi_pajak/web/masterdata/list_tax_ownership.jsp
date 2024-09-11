<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Daftar Kepemilikan Pajak</title>
    <style>
        /* Scoped styles to prevent interference with the sidebar and other parts of index.jsp */
        .tax-ownership-page {
            font-family: 'Roboto', sans-serif;
            background-color: #f4f7f9;
            padding: 20px;
        }

        .tax-ownership-page .main-content {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            overflow: auto;
            margin: 20px; /* Margin to separate from other content if needed */
        }

        .tax-ownership-page .main-content h1 {
            font-size: 24px;
            margin-bottom: 20px;
            color: #333;
        }

        .tax-ownership-page table {
            width: 100%;
            border-collapse: collapse;
        }

        .tax-ownership-page table, .tax-ownership-page th, .tax-ownership-page td {
            border: 1px solid #ddd;
        }

        .tax-ownership-page th, .tax-ownership-page td {
            padding: 12px;
            text-align: left;
            font-size: 14px;
        }

        .tax-ownership-page th {
            background-color: #3a5ba0;
            color: white;
        }

        .tax-ownership-page tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        .tax-ownership-page tr:hover {
            background-color: #f1f1f1;
        }

        .tax-ownership-page .no-data {
            text-align: center;
            color: #666;
            padding: 20px;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
    <div class="tax-ownership-page">
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
                        <td><%= taxOwnership.getTanggalPembayaran() != null ? taxOwnership.getTanggalPembayaran() : "" %></td>
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
