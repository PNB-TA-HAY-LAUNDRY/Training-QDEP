<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.form.pajak.CtrlTaxVehicle"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
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
            margin: 0 auto;
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

        /* Styling for action icons */
        .action-icons {
            display: flex;
            justify-content: space-around;
            gap: 10px;
        }

        .action-icons a {
            text-decoration: none;
            color: var(--primary-color);
            font-size: 20px;
        }

        .action-icons a:hover {
            color: var(--secondary-color);
        }

        .add-button {
            margin-bottom: 20px;
            padding: 10px 15px;
            background-color: var(--primary-color);
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }

        .add-button:hover {
            background-color: var(--secondary-color);
        }
    </style>
</head>
<body>

    <div class="container">
        <div class="content">
            <h1>Daftar Pajak Kendaraan</h1>
            
            
        <form action="taxOwnershipForm.jsp" method="get">
            <input type="hidden" name="command" value="<%= Command.ADD %>" />
            <button type="submit" class="add-button">Tambah Data</button>
        </form>
            
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
                    String currentPage = "list_tax_vehicle";
                    
                    Vector<TaxVehicle> listTypes = new Vector();
                    
                    int iCommand = FRMQueryString.requestCommand(request);
                    
                    CtrlTaxVehicle ctrlTaxVehicle = new CtrlTaxVehicle(request);

                    try {
                        ctrlTaxVehicle.action(Command.LIST, 0);
                        listTypes = (Vector<TaxVehicle>) request.getAttribute("taxvehicles");
                    } catch (Exception e) {
                        log("Error: " + e); 
                    }
                %>
               <% if (listTypes == null || listTypes.isEmpty()) { %>  
                <tr>
                    <td colspan="12" class="no-data">Tidak ada data kepemilikan pajak tersedia.</td>
                </tr>
               <% } else { 
                   int index = 1; 
                   for (TaxVehicle taxVehicle : listTypes) {
                       String taxTypeName = "";
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
                        <td><%= index++ %></td>
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
                        <td><%= taxVehicle.getTanggalPembayaran() != null ? taxVehicle.getTanggalPembayaran() : "" %></td>
                    </tr>
                    <%
                        }}
                    %>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
