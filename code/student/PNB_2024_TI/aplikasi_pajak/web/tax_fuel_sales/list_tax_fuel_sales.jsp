<%@ page import="com.dimata.util.Command"%>
<%@ page import="com.dimata.form.pajak.CtrlTaxFuelSales"%>
<%@ page import="com.dimata.qdep.form.FRMQueryString"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.Collections"%>
<%@ page import="java.util.Comparator"%>
<%@ page import="com.dimata.entity.pajak.TaxFuelSales"%>
<%@ page import="com.dimata.entity.pajak.TaxType"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Daftar Pajak Penjualan Bahan Bakar</title>
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
    <div class="main-content">
        <h1>Daftar Pajak Penjualan Bahan Bakar</h1>

        <!-- Add Data Button -->
        <form action="${pageContext.request.contextPath}/fuel_sales_tax_records/frm_tax_fuel_sales.jsp" method="get">
            <button type="button" class="add-button" onclick="window.location.href='${pageContext.request.contextPath}/tax_fuel_sales/frm_tax_fuel_sales.jsp?command=<%= Command.ADD %>'">
                Tambah Data
            </button>
        </form>

        <table>
            <thead>
                <tr>
                    <th>No</th>
                    <th>Penjual</th>
                    <th>Jumlah Liter</th>
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
                    String currentPage = "list_tax_fuel_sales.jsp";
                    Vector<TaxFuelSales> taxFuelSalesList = new Vector();
                    int iCommand = FRMQueryString.requestCommand(request);
                    CtrlTaxFuelSales ctrlTaxFuelSales = new CtrlTaxFuelSales(request);

                    if (iCommand == Command.ADD) {
                        response.sendRedirect("form_tax_fuel_sales.jsp");
                    }

                    try {
                        ctrlTaxFuelSales.action(Command.LIST, 0);
                        taxFuelSalesList = (Vector<TaxFuelSales>) request.getAttribute("taxFuelSalesList");
                        
                        if (taxFuelSalesList != null && !taxFuelSalesList.isEmpty()) {
                            Collections.sort(taxFuelSalesList, new Comparator<TaxFuelSales>() {
                                public int compare(TaxFuelSales t1, TaxFuelSales t2) {
                                    return t1.getTanggalPenjualan().compareTo(t2.getTanggalPenjualan());
                                }
                            });
                        }
                    } catch (Exception e) {
                        log("Error: " + e);
                    }
                %>
                <% if (taxFuelSalesList == null || taxFuelSalesList.isEmpty()) { %>
                <tr>
                    <td colspan="10" class="no-data">Tidak ada data pajak penjualan bahan bakar.</td>
                </tr>
                <% } else { 
                    int index = 1;
                    for (TaxFuelSales taxFuelSales : taxFuelSalesList) {
                        String taxTypeName = "";
                        try {
                            TaxType taxType = taxFuelSales.getTaxType();
                            if (taxType != null) {
                                taxTypeName = taxType.getNamaPajak();
                            }
                        } catch (Exception e) {
                            taxTypeName = "Unknown";
                        }
                %>
                <tr>
                    <td><%= index++ %></td>
                    <td><%= taxFuelSales.getPenjual() %></td>
                    <td><%= taxFuelSales.getJumlahLiter() %></td>
                    <td><%= taxTypeName %></td>
                    <td><%= taxFuelSales.getJumlahPajak() %></td>
                    <td><%= taxFuelSales.getTanggalPenjualan() %></td>
                    <td><%= taxFuelSales.getTanggalJatuhTempo() %></td>
                    <td><%= taxFuelSales.getStatusPembayaran() %></td>
                    <td><%= taxFuelSales.getTanggalPembayaran() != null ? taxFuelSales.getTanggalPembayaran() : "" %></td>

                  
                </tr>
                <% } } %>
            </tbody>
        </table>
    </div>
</body>
</html>