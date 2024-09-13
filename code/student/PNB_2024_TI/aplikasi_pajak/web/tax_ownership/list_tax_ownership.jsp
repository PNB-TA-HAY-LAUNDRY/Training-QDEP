<%@page import="com.dimata.qdep.system.I_DBExceptionInfo"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.form.pajak.CtrlTaxOwnership"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.entity.pajak.TaxOwnership"%>
<%@page import="com.dimata.entity.pajak.TaxType"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String currentPage = "list_tax_ownership";

    Vector<TaxOwnership> listTypes = new Vector();

    int iCommand = FRMQueryString.requestCommand(request);

    long idDeleteTax = FRMQueryString.requestLong(request, "idDeleteTax");

    CtrlTaxOwnership ctrlTaxOwnership = new CtrlTaxOwnership(request);

    boolean success = false;

    // Handle DELETE command
    if (iCommand == Command.DELETE) {
        if (idDeleteTax != 0) {
            try {
                // Attempt to delete asset list by ID
                int action = ctrlTaxOwnership.action(iCommand, idDeleteTax);
                if (action == I_DBExceptionInfo.NO_EXCEPTION) {
                    success = true;
                }
            } catch (Exception e) {
                out.println("Error delete data: " + e);
            }
        }
    }

    if (iCommand == Command.ADD) {
        response.sendRedirect("taxOwnershipForm.jsp");
    }

    try {
        ctrlTaxOwnership.action(Command.LIST, 0);
        listTypes = (Vector<TaxOwnership>) request.getAttribute("taxOwnerships");
    } catch (Exception e) {
        log("Error: " + e);
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

            html, body {
                height: 100%;
                margin: 0;
                font-family: 'Roboto', sans-serif;
                background-color: var(--main-bg-color);
            }

            .main-content {
                background-color: var(--card-bg-color);
                padding: 30px;
                margin: 20px;
                border-radius: 10px;
                box-shadow: var(--card-shadow);
                overflow: auto;
            }

            .main-content h1 {
                font-size: 24px;
                margin-bottom: 20px;
                color: var(--text-color);
                font-weight: 400;
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
            <h1>Daftar Kepemilikan Pajak Kendaraan</h1>

            <!-- Tambahkan tombol "Tambah Data" -->
            <form action="tax_ownership/taxOwnershipForm.jsp" method="get">
                <input type="hidden" name="command" value="<%= Command.ADD%>" />
                <button type="submit" class="add-button">Tambah Data</button>
            </form>


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
                    <% if (listTypes == null || listTypes.isEmpty()) { %>  
                    <tr>
                        <td colspan="12" class="no-data">Tidak ada data kepemilikan pajak tersedia.</td>
                    </tr>
                    <% } else {
                        int index = 1;
                        for (TaxOwnership taxOwnership : listTypes) {
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
                        <td><%= index++%></td> 
                        <td><%= taxOwnership.getNoPlat()%></td> 
                        <td><%= taxOwnership.getNamaPemilikLama()%></td>
                        <td><%= taxOwnership.getNamaPemilikBaru()%></td> 
                        <td><%= taxOwnership.getAlamatBaru()%></td> 
                        <td><%= taxTypeName%></td>
                        <td><%= taxOwnership.getJumlahPajak()%></td> 
                        <td><%= taxOwnership.getTanggalProses()%></td> 
                        <td><%= taxOwnership.getTanggalJatuhTempo()%></td> 
                        <td><%= taxOwnership.getStatusPembayaran()%></td> 
                        <td><%= taxOwnership.getTanggalPembayaran() != null ? taxOwnership.getTanggalPembayaran() : ""%></td>
                    </tr>
                    <% }
                   }%>
                </tbody>
            </table>
        </div>
    </body>
</html>
