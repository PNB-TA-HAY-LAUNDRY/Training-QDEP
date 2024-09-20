<%-- 
    Document   : list_tax_vehicle
    Created on : Sep 20, 2024, 5:13:22 PM
    Author     : ihsan
--%>

<%@page import="com.dimata.qdep.system.I_DBExceptionInfo"%>
<%@page import="java.util.List"%>
<%@page import="com.dimata.entity.pajak.PstTaxType"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.form.pajak.CtrlTaxVehicle"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.entity.pajak.TaxType"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.entity.pajak.TaxVehicle"%>
<%@page import="com.dimata.entity.pajak.PstTaxVehicle"%>
<%@page import="com.dimata.entity.pajak.PaymentStatus"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String currentPage = "list_tax_vehicle";
    Vector<TaxVehicle> listTypes = new Vector();
    long idDeleteTax = FRMQueryString.requestLong(request, "idDeleteTax");
    CtrlTaxVehicle ctrlTaxVehicle = new CtrlTaxVehicle(request);
    boolean success = false;
    boolean deleteAttempted = false; // Flag untuk melacak apakah penghapusan dicoba

    out.println("Received ID for delete: " + idDeleteTax);

    if (idDeleteTax > 0) {
        deleteAttempted = true; // Penghapusan dicoba
        try {
            int action = ctrlTaxVehicle.actionDelete(idDeleteTax);
            if (action == I_DBExceptionInfo.NO_EXCEPTION) {
                success = true;
                out.println("");
            } else {
                out.println("Gagal menghapus data: Kode error " + action);
            }
        } catch (Exception e) {
            out.println("Error saat menghapus data: " + e.getMessage());
        }
    } else if (idDeleteTax != 0) {
        out.println("ID tidak valid untuk penghapusan.");
    }

    if (deleteAttempted && !success) {
        out.println("<p>Penghapusan gagal. Harap periksa kembali.</p>");
    }

    try {
        ctrlTaxVehicle.action(Command.LIST, 0);
        listTypes = (Vector<TaxVehicle>) request.getAttribute("taxVehicles");
    } catch (Exception e) {
        log("Error retrieving data: " + e.getMessage());
    }

    if (success) {
        out.println("<p>Data deleted successfully.</p>");
    }
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Daftar Pajak Kendaraan</title>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            #data-table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
                background-color: var(--card-bg-color);
                box-shadow: var(--card-shadow);
            }

            #data-table th, #data-table td {
                padding: 8px; /* Reduced padding for a more compact look */
                text-align: left;
                border-bottom: 1px solid var(--border-color);
                font-size: 12px; /* Reduced font size */
            }

            #data-table th {
                background-color: #3498db;
                color: white;
                font-weight: bold;
            }

            #data-table tr:nth-child(even) {
                background-color: #f9f9f9;
            }

            #data-table tr:hover {
                background-color: #f1f1f1;
            }

            #data-table td {
                font-size: 12px; /* Ensure font size matches cell padding */
            }

            .delete-btn {
                background-color: #e74c3c;
                color: white;
                border: none;
                padding: 4px 8px; /* Reduced padding for the button */
                border-radius: 4px;
                cursor: pointer;
                font-size: 12px; /* Reduced font size */
            }

            .delete-btn:hover {
                background-color: #c0392b;
            }

            .no-data {
                text-align: center;
                padding: 10px; /* Reduced padding for the no-data message */
                color: var(--text-muted);
                font-size: 14px; /* Adjusted font size */
                font-weight: bold;
            }


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
            }

            .main-content h1 {
                font-size: 24px;
                margin-bottom: 20px;
                color: var(--text-color);
                font-weight: 400;
            }

            form {
                display: grid;
                grid-template-columns: 1fr 1fr;
                gap: 20px;
            }

            label {
                font-weight: bold;
            }

            input, select {
                padding: 10px;
                width: 100%;
                border: 1px solid var(--border-color);
                border-radius: 4px;
                box-sizing: border-box;
            }

            .form-buttons {
                grid-column: span 2;
                display: flex;
                justify-content: flex-end;
            }

            .submit-button {
                padding: 10px 20px;
                background-color: #3498db;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 16px;
            }

            .submit-button:hover {
                background-color: var(--secondary-color);
            }

            .form-container {
                display: none;
                background-color: var(--card-bg-color);
                padding: 30px;
                margin-top: 20px;
                border-radius: 10px;
                box-shadow: var(--card-shadow);
            }

            .form-toggle-button {
                background-color: #3498db;
                color: white;
                border: none;
                padding: 10px 20px;
                cursor: pointer;
                border-radius: 4px;
                font-size: 16px;
                margin-top: 20px;
            }

            .form-toggle-button:hover {
                background-color: var(--secondary-color);
            }

            .no-data {
                text-align: center;
                color: var(--text-muted);
            }

            #page-content-wrapper {
                margin-left: 250px; /* Ensure this aligns with your sidebar width */
                padding: 20px;
                transition: margin-left 0.3s ease;
            }
            .form-toggle-button i {
                margin-right: 8px; /* Jarak antara ikon dan teks */
            }
            .btn-delete {
                display: inline-block;
                padding: 8px 12px;
                color: #fff;
                background-color: #e74c3c;
                text-decoration: none;
                border-radius: 4px;
                border: none;
                font-size: 14px;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            .btn-delete:hover {
                background-color: #c0392b;
            }

        </style>
        <script>
            
            function toggleForm() {
                var formContainer = document.getElementById('formContainer');
                formContainer.style.display = (formContainer.style.display === 'none' || formContainer.style.display === '') ? 'block' : 'none';
            }
            
        function confirmDelete(id) {
        Swal.fire({
            title: 'Apakah Anda yakin?',
            text: "Data ini akan dihapus dan tidak dapat dikembalikan!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Ya, hapus!',
            cancelButtonText: 'Batal'
        }).then((result) => {
            if (result.isConfirmed) {
                // Jika user konfirmasi penghapusan, redirect ke URL penghapusan dengan ID
                window.location.href = 'list_tax_vehicle.jsp?idDeleteTax=' + id;
            }
        })
    }

            
                
        </script>
    </head>

    <body>
        <%@ include file="/sidebar.jsp" %>


        <div id="page-content-wrapper">
            <div class="main-content">
                <h1>Daftar Pajak Kendaraan</h1>

                <!-- Button to toggle form display -->
                <button class="form-toggle-button" onclick="toggleForm()">Tambah Data Pajak Kendaraan</button>

                <!-- Form container -->
                <div id="formContainer" class="form-container">
                    <form method="post">
                        <!-- Form Fields -->
                        <label for="noPlat">Nomor Plat:</label>
                        <input type="text" id="noPlat" name="noPlat" required />

                        <label for="namaPemilik">Nama Pemilik:</label>
                        <input type="text" id="namaPemilik" name="namaPemilik" required />

                        <label for="alamat">Alamat:</label>
                        <input type="text" id="alamat" name="alamat" required />

                        <label for="merk">Merk:</label>
                        <input type="text" id="merk" name="merk" required />

                        <label for="model">Model:</label>
                        <input type="text" id="model" name="model" required />

                        <label for="tahunPembuatan">Tahun Pembuatan:</label>
                        <input type="text" id="tahunPembuatan" name="tahunPembuatan" required />

                        <label for="taxTypeId">Jenis Pajak:</label>
                        <select id="taxTypeId" name="namaPajak">
                            <%-- Menampilkan opsi jenis pajak berdasarkan data dari database --%>
                            <%
                                List<TaxType> taxTypes = PstTaxType.listAll(0, 0, "", "");
                                for (TaxType taxType : taxTypes) {
                            %>
                            <option value="<%= taxType.getNamaPajak()%>"><%= taxType.getNamaPajak()%></option>
                            <% } %>
                        </select>

                        <label for="jumlahPajak">Jumlah Pajak:</label>
                        <input type="number" id="jumlahPajak" name="jumlahPajak" required />

                        <label for="periodePajak">Periode Pajak:</label>
                        <input type="text" id="periodePajak" name="periodePajak" required />

                        <label for="statusPembayaran">Status Pembayaran:</label>
                        <select id="statusPembayaran" name="statusPembayaran">
                            <option value="BELUM_DIBAYAR">Belum Dibayar</option>
                            <option value="DIBAYAR">Dibayar</option>
                        </select>


                        <label for="tanggalJatuhTempo">Tanggal Jatuh Tempo:</label>
                        <input type="date" id="tanggalJatuhTempo" name="tanggalJatuhTempo" required />

                        <label for="tanggalPembayaran">Tanggal Pembayaran:</label>
                        <input type="date" id="tanggalPembayaran" name="tanggalPembayaran" />

                        <div class="form-buttons">
                            <button type="submit" class="submit-button">Simpan</button>
                        </div>
                    </form>
                </div>
                <%-- Proses Data Jika Form Disubmit --%>
                <%
                    if ("POST".equalsIgnoreCase(request.getMethod())) {
                        try {
                            TaxVehicle taxVehicle = new TaxVehicle();
                            taxVehicle.setNoPlat(request.getParameter("noPlat"));
                            taxVehicle.setNamaPemilik(request.getParameter("namaPemilik"));
                            taxVehicle.setAlamat(request.getParameter("alamat"));
                            taxVehicle.setMerk(request.getParameter("merk"));
                            taxVehicle.setModel(request.getParameter("model"));
                            taxVehicle.setTahunPembuatan(request.getParameter("tahunPembuatan"));
                            taxVehicle.setPeriodePajak(request.getParameter("periodePajak"));

                            // Ambil Nama Pajak dari form
                            String namaPajak = request.getParameter("namaPajak");

                            // Cari TaxType berdasarkan namaPajak
                            TaxType taxType = PstTaxType.fetchByName(namaPajak);
                            if (taxType == null) {
                                throw new Exception("Jenis pajak tidak ditemukan.");
                            }
                            taxVehicle.setTaxType(taxType);

                            // Pengecekan dan parsing jumlah pajak
                            String jumlahPajakStr = request.getParameter("jumlahPajak");
                            if (jumlahPajakStr != null && !jumlahPajakStr.isEmpty()) {
                                taxVehicle.setJumlahPajak(Double.parseDouble(jumlahPajakStr));
                            } else {
                                throw new Exception("Jumlah pajak tidak boleh kosong.");
                            }

                            // Status Pembayaran
                            String statusPembayaranStr = request.getParameter("statusPembayaran");
                            if (statusPembayaranStr != null && !statusPembayaranStr.isEmpty()) {
                                PaymentStatus statusPembayaran = PaymentStatus.valueOf(statusPembayaranStr.toUpperCase());
                                taxVehicle.setStatusPembayaran(statusPembayaran);
                            } else {
                                throw new Exception("Status pembayaran tidak boleh kosong.");
                            }

                            // Pengecekan dan parsing tanggal jatuh tempo
                            String tanggalJatuhTempoStr = request.getParameter("tanggalJatuhTempo");
                            if (tanggalJatuhTempoStr != null && !tanggalJatuhTempoStr.isEmpty()) {
                                taxVehicle.setTanggalJatuhTempo(java.sql.Date.valueOf(tanggalJatuhTempoStr));
                            } else {
                                throw new Exception("Tanggal jatuh tempo tidak boleh kosong.");
                            }

                            // Pengecekan dan parsing tanggal pembayaran (opsional)
                            String tanggalPembayaranStr = request.getParameter("tanggalPembayaran");
                            if (tanggalPembayaranStr != null && !tanggalPembayaranStr.isEmpty()) {
                                taxVehicle.setTanggalPembayaran(java.sql.Date.valueOf(tanggalPembayaranStr));
                            }

                            // membuat instance PstTaxOwnership untuk memanggil metode insertExc()
                            PstTaxVehicle pstTaxVehicle = new PstTaxVehicle();

                            // menggunakan metode insertExc untuk memasukkan data ke database
                            pstTaxVehicle.insertExc(taxVehicle);

                            response.sendRedirect("list_tax_vehicle.jsp");
                            
                        } catch (NumberFormatException e) {
                            out.println("<p>Format jumlah pajak tidak valid: " + e.getMessage() + "</p>");
                        } catch (IllegalArgumentException e) {
                            out.println("<p>Format status pembayaran tidak valid: " + e.getMessage() + "</p>");
                        } catch (Exception e) {
                            out.println("<p>Error: " + e.getMessage() + "</p>"); // Menampilkan error di halaman
                            e.printStackTrace(); // Menampilkan stack trace di console
                        }

                    }
                %>

                <table id="data-table" border="1">
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
                            <th>Status Pembayaran</th>  
                            <th>Tanggal Jatuh Tempo</th>
                            <th>Tanggal Pembayaran</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (listTypes == null || listTypes.isEmpty()) { %>  
                        <tr>
                            <td colspan="14" class="no-data">Tidak ada data pajak tersedia.</td>
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
                            <td><%= index++%></td>
                            <td><%= taxVehicle.getNoPlat()%></td>
                            <td><%= taxVehicle.getNamaPemilik()%></td>
                            <td><%= taxVehicle.getAlamat()%></td>
                            <td><%= taxVehicle.getMerk()%></td>
                            <td><%= taxVehicle.getModel()%></td>
                            <td><%= taxVehicle.getTahunPembuatan()%></td>
                            <td><%= taxTypeName%></td>
                            <td><%= taxVehicle.getJumlahPajak()%></td>
                            <td><%= taxVehicle.getPeriodePajak()%></td>
                            <td><%= taxVehicle.getStatusPembayaran()%></td>
                            <td><%= taxVehicle.getTanggalJatuhTempo()%></td>
                            <td><%= taxVehicle.getTanggalPembayaran() != null ? taxVehicle.getTanggalPembayaran() : ""%></td>
                            <td>
                                <button class="btn-delete" onclick="confirmDelete(<%= taxVehicle.getVehicleTaxId() %>)"> Hapus </button>
                            </td>
                        </tr>
                        <%
                            }
                        }%>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
