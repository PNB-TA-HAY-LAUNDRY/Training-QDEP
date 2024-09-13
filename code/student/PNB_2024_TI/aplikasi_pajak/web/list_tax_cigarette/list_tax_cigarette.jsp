<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.form.pajak.CtrlTaxCigarette"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.entity.pajak.TaxCigarette"%>
<%@page import="com.dimata.entity.pajak.TaxType"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Daftar Pajak Rokok</title>
    <style>
        /* Consistent Styles with the second JSP */
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
        .add-button {
            margin-bottom: 20px;
            padding: 10px 15px;
            background-color: #3a5ba0;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .add-button:hover {
            background-color: #426DAE;
        }
    </style>
</head>
<body>
    <div class="main-content-wrapper">
        <div class="main-content">
            <h1>Daftar Pajak Rokok</h1>
            
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
                        <th>Penjual</th>
                        <th>Jumlah Batang</th>
                        <th>Jenis Pajak</th>
                        <th>Jumlah Pajak</th>
                        <th>Tanggal Penjualan</th>
                        <th>Tanggal Jatuh Tempo</th>
                        <th>Status Pembayaran</th>
                        <th>Tanggal Pembayaran</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        Vector<TaxCigarette> listCigarettes = new Vector();
                        int iCommand = FRMQueryString.requestCommand(request);
                        CtrlTaxCigarette ctrlTaxCigarette = new CtrlTaxCigarette(request);

                        if (iCommand == Command.ADD) {
                            response.sendRedirect("frm_tax_cigarette.jsp");
                        }

                        try {
                            ctrlTaxCigarette.action(Command.LIST, 0);
                            listCigarettes = (Vector<TaxCigarette>) request.getAttribute("taxCigarettes");
                        } catch (Exception e) {
                            log("Error: " + e);
                        }
                    %>
                    <% if (listCigarettes == null || listCigarettes.isEmpty()) { %>  
                    <tr>
                        <td colspan="9" class="no-data">Tidak ada data pajak rokok tersedia.</td>
                    </tr>
                    <% } else { 
                        int index = 1; 
                        for (TaxCigarette taxCigarette : listCigarettes) {
                            String taxTypeName = "";
                            try {
                                TaxType taxType = taxCigarette.getTaxType();
                                if (taxType != null) {
                                    taxTypeName = taxType.getNamaPajak();
                                }
                            } catch (Exception e) {
                                taxTypeName = "Unknown";
                            }
                    %>
                    <tr>
                        <td><%= index++ %></td> 
                        <td><%= taxCigarette.getPenjual() %></td> 
                        <td><%= taxCigarette.getJumlahBatang() %></td>
                        <td><%= taxTypeName %></td>
                        <td><%= taxCigarette.getJumlahPajak() %></td> 
                        <td><%= taxCigarette.getTanggalPenjualan() %></td> 
                        <td><%= taxCigarette.getTanggalJatuhTempo() %></td> 
                        <td><%= taxCigarette.getStatusPembayaran() != null ? taxCigarette.getStatusPembayaran() : "" %></td>
                        <td><%= taxCigarette.getTanggalPembayaran() != null ? taxCigarette.getTanggalPembayaran() : "" %></td>
                    </tr>
                    <% } } %>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
