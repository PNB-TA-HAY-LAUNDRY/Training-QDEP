<%-- 
    Document   : list_tax_water
    Created on : Sep 12, 2024, 3:12:47 PM
    Author     : andin
--%>

<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.form.pajak.CtrlTaxWater"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.entity.pajak.TaxWater"%>
<%@page import="com.dimata.entity.pajak.TaxType"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Daftar Tagihan Pajak Air</title>
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
    <div class="main-content">
        <h1>Daftar Tagihan Pajak Air</h1>
        
        <!-- Tombol Tambah Data -->
        <form action="${pageContext.request.contextPath}/tax_water/frm_tax_water.jsp" method="get">
            <button type="button" class="add-button" onclick="window.location.href='${pageContext.request.contextPath}/tax_water/form_tax_water.jsp?command=<%= Command.ADD %>'">
                Tambah Data
            </button>
        </form>

        <table>
            <thead>
                <tr>
                    <th>No</th>
                    <th>Pengguna</th>
                    <th>Alamat</th>
                    <th>Volume Air (m<sup>3</sup>)</th>
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
                    
                    String currentPage = "list_tax_water";
                    Vector<TaxWater> listTaxWater = new Vector();
                    CtrlTaxWater ctrlTaxWater = new CtrlTaxWater(request);
                    int iCommand = FRMQueryString.requestCommand(request);

                    if (iCommand == Command.ADD) {
                        response.sendRedirect("form_tax_water.jsp");
                    }

                    try {
                        ctrlTaxWater.action(Command.LIST, 0);
                        listTaxWater = (Vector<TaxWater>) request.getAttribute("taxWaters");
                    } catch (Exception e) {
                        log("Error: " + e); 
                    }

                    if (listTaxWater == null || listTaxWater.isEmpty()) {
                %>
                <tr>
                    <td colspan="11" class="no-data">Tidak ada data tagihan pajak air tersedia.</td>
                </tr>
                <% } else { 
                    int index = 1; 
                    for (TaxWater taxWater : listTaxWater) {
                        String taxTypeName = "";
                        try {
                            taxTypeName = taxWater.getTaxType() != null ? taxWater.getTaxType().getNamaPajak() : "Unknown";
                        } catch (Exception e) {
                            taxTypeName = "Unknown";
                        }
                %>
                <tr>
                    <td><%= index++ %></td>
                    <td><%= taxWater.getPengguna() %></td>
                    <td><%= taxWater.getLokasi() %></td>
                    <td><%= taxWater.getVolumeAirM3() %></td>
                    <td><%= taxTypeName %></td>
                    <td><%= taxWater.getJumlahPajak() %></td>
                    <td><%= taxWater.getPeriodePajak() %></td>
                    <td><%= taxWater.getTanggalJatuhTempo() %></td>
                    <td><%= taxWater.getStatusPembayaran() %></td>
                    <td><%= taxWater.getTanggalPembayaran() != null ? taxWater.getTanggalPembayaran() : "" %></td>

                    
                </tr>
                <% } } %>
            </tbody>
        </table>
    </div>
</body>
</html>

