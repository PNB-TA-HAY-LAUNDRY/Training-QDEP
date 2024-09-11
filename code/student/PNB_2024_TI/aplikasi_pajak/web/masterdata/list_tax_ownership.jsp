<%-- 
    Document   : list_tax_ownership
    Created on : Sep 11, 2024, 11:06:13 PM
    Author     : ihsan
--%>

<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.form.pajak.CtrlTaxOwnership"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.entity.pajak.TaxOwnership"%>
<%@page import="com.dimata.entity.pajak.TaxType"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // This defines the current page as 'asset_list' for use within the page
    String currentPage = "list_tax_ownership"; // Sesuaikan nama halaman di sini
%>
<%
    // Declare a vector to hold asset types, asset conditions, asset lists
    Vector<TaxOwnership> listTypes = new Vector();

    // Retrieve the current command sent via the request, e.g., ADD, DELETE, etc.
    int iCommand = FRMQueryString.requestCommand(request);

    // Initialize controller for managing asset list 
    CtrlTaxOwnership ctrlTaxOwnership = new CtrlTaxOwnership(request);

    // Fetch list of asset types from the database
    try {
        ctrlTaxOwnership.action(Command.LIST, 0);

        listTypes = (Vector<TaxOwnership>) request.getAttribute("taxOwnerships");
    } catch (Exception e) {
        log("Error: " + e);  // Use log() instead of System.out.println()
    }


%>
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
               <%  // Cek jika listAssets kosong atau null
                if (listTypes == null || listTypes.isEmpty()) {
               %>  
            <tr>
                <td colspan="11" class="no-data">Tidak ada data kepemilikan pajak tersedia.</td>
            </tr>
               <%  } else { 
               int index = 1; // Initialize row number
               for (TaxOwnership taxOwnership : listTypes) {
               %>
               <% 
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
                <td><%= index++ %></td> <!-- Row number -->
                <td><%= taxOwnership.getNoPlat() %></td> <!-- No Plat -->
                <td><%= taxOwnership.getNamaPemilikLama() %></td> <!-- Nama Pemilik Lama -->
                <td><%= taxOwnership.getNamaPemilikBaru() %></td> <!-- Nama Pemilik Baru -->
                <td><%= taxOwnership.getAlamatBaru() %></td> <!-- Alamat Baru -->
                <td><%= taxTypeName %></td> <!-- Jenis Pajak -->
                <td><%= taxOwnership.getJumlahPajak() %></td> <!-- Jumlah Pajak -->
                <td><%= taxOwnership.getTanggalProses() %></td> <!-- Tanggal Proses -->
                <td><%= taxOwnership.getTanggalJatuhTempo() %></td> <!-- Tanggal Jatuh Tempo -->
                <td><%= taxOwnership.getStatusPembayaran() %></td> <!-- Status Pembayaran -->
                <td><%= taxOwnership.getTanggalPembayaran() != null ? (taxOwnership.getTanggalPembayaran()) : "" %></td>
            </tr>
<%      }
    } 
%>
            </tbody>
        </table>
    </div>
</body>
</html>

