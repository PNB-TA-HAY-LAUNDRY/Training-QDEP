<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.form.pajak.CtrlTaxOwnership"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.entity.pajak.TaxOwnership"%>
<%@page import="com.dimata.entity.pajak.TaxType"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <h1>Daftar Kepemilikan Pajak</h1>
        
      <!-- Add Data Button in Form using JavaScript -->
    <form action="${pageContext.request.contextPath}/tax_ownership/frm_tax_ownership.jsp" method="get">
        <button type="button" class="btn btn-primary btn-sm mt-4 mb-3" onclick="window.location.href='${pageContext.request.contextPath}/tax_ownership/frm_tax_ownership.jsp?command=<%= Command.ADD %>'">
            <i class="fas fa-plus"></i> Tambah Data
        </button>
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
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    String currentPage = "list_tax_ownership";
                    
                    Vector<TaxOwnership> listTypes = new Vector();
                    
                    int iCommand = FRMQueryString.requestCommand(request);
                    
                    CtrlTaxOwnership ctrlTaxOwnership = new CtrlTaxOwnership(request);
                    
                    // Handle ADD command by redirecting to the form page for adding a new asset list
                    if (iCommand == Command.ADD) {
                        response.sendRedirect("form_tax_ownership.jsp");
                    }

                    try {
                        ctrlTaxOwnership.action(Command.LIST, 0);
                        listTypes = (Vector<TaxOwnership>) request.getAttribute("taxOwnerships");
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
                    <td><%= index++ %></td> 
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

                    <!-- Action icons for update and delete -->
                    <td class="action-icons">
                        <a href="form_update_tax_ownership.jsp?id="Edit">
                            &#x270E; <!-- Edit icon (pencil) -->
                        </a>
                        <a href="process_delete_tax_ownership.jsp?id=" title="Delete" 
                           onclick="return confirm('Yakin ingin menghapus data ini?');">
                            &#x1F5D1; <!-- Delete icon (trash can) -->
                        </a>
                    </td>
                </tr>
               <% } } %>
            </tbody>
        </table>
    </div>
</body>
</html>
