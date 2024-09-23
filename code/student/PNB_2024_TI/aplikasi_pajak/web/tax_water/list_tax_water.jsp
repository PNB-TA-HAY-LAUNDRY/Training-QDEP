<%-- 
    Document   : list_tax_water
    Created on : Sep 12, 2024, 3:12:47 PM
    Author     : andin
--%>

<%@page import="com.dimata.entity.pajak.PstTaxWater"%>
<%@page import="com.dimata.entity.pajak.PaymentStatus"%>
<%@page import="java.util.List"%>
<%@page import="com.dimata.entity.pajak.PstTaxType"%>
<%@page import="com.dimata.qdep.system.I_DBExceptionInfo"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.form.pajak.CtrlTaxWater"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.entity.pajak.TaxWater"%>
<%@page import="com.dimata.entity.pajak.TaxType"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String currentPage = "list_tax_water";
    Vector<TaxWater> listTypes = new Vector();
    // Ambil idDeleteTax dari request
    long idDeleteTax = FRMQueryString.requestLong(request, "idDeleteTax");
String message = "";
    CtrlTaxWater ctrlTaxWater = new CtrlTaxWater(request);
    boolean success = false;
   



// Menangani perintah DELETE
if (idDeleteTax > 0) {

    
    try {
        // Panggil metode actionDelete dengan idDeleteTax
        int action = ctrlTaxWater.actionDelete(idDeleteTax);
        if (action == I_DBExceptionInfo.NO_EXCEPTION) {
            success = true;
            out.println("");
        } else {
            out.println("" + action);
        }
    } catch (Exception e) {
        out.println("" + e.getMessage());
    }
} else {
    out.println("");
}

// Menampilkan hasil aksi penghapusan
if (success) {
    // Tampilkan pesan sukses atau logika lain di halaman yang sama
    out.println("");
} else {
    // Tampilkan pesan kesalahan di halaman yang sama
    out.println("");
}



    // Always list data
    try {
        ctrlTaxWater.action(Command.LIST, 0);
        listTypes = (Vector<TaxWater>) request.getAttribute("taxWaters");
    } catch (Exception e) {
        log("Error retrieving data: " + e.getMessage());
    }

    if (success) {
        out.println("<p>Data deleted successfully.</p>");
    }
    

%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Daftar Pajak Air</title>
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

            /* New CSS for dialog */
            .dialog-overlay {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.5);
                display: none;
                justify-content: center;
                align-items: center;
                z-index: 1000;
            }

            .dialog {
                background: white;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                max-width: 400px;
                width: 100%;
                text-align: center;
            }

            .dialog button {
                padding: 10px 20px;
                background-color: var(--primary-color);
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 16px;
            }

            .dialog button:hover {
                background-color: var(--secondary-color);
            }

            #page-content-wrapper {
                margin-left: 250px; /* Ensure this aligns with your sidebar width */
                padding: 20px;
                transition: margin-left 0.3s ease;
            }
            .form-toggle-button i {
                margin-right: 8px; /* Jarak antara ikon dan teks */
            }

        </style>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
            function toggleForm() {
                var formContainer = document.getElementById('formContainer');
                formContainer.style.display = (formContainer.style.display === 'none' || formContainer.style.display === '') ? 'block' : 'none';
            }

//            function showDialog(message) {
//                var overlay = document.getElementById('dialogOverlay');
//                var dialog = document.getElementById('dialog');
//                dialog.innerHTML = '<p>' + message + '</p><button onclick="hideDialog()">OK</button>';
//                overlay.style.display = 'flex';
//            }
//
//            function hideDialog() {
//                document.getElementById('dialogOverlay').style.display = 'none';
//            }
//
//            $(document).ready(function () {
//                $('form').on('submit', function (event) {
//                    event.preventDefault(); // Mencegah pengiriman form default
//
//                    var form = $(this);
//                    $.ajax({
//                        type: 'POST',
//                        url: 'process_tax_ownership.jsp', // URL JSP untuk memproses data form
//                        data: form.serialize(), // Mengirim data form
//                        success: function (response) {
//                            // Update tabel dengan data terbaru
//                            $('#data-table tbody').html($(response).find('#data-table tbody').html());
//                            // Reset form
//                            form[0].reset();
//                            // Sembunyikan form setelah submit
//                            toggleForm();
//                            // Tampilkan dialog message
//                            showDialog('Data berhasil disimpan!');
//                        },
//                        error: function (xhr, status, error) {
//                            console.error('Terjadi kesalahan: ' + error);
//                        }
//                    });
//                });
//            });




        </script>
    </head>
    <body>
        
    <%@ include file="/sidebar.jsp"%>
        <div id= "page-content-wrapper"> 
                    
        <div class="main-content">
            <h1>Daftar Pajak Air</h1>

            <!-- Button to toggle form display -->
            <button class="form-toggle-button" onclick="toggleForm()">Tambah Data</button>

            <!-- Form container -->
            <div id="formContainer" class="form-container">
                <form method="post">
                    <!-- Form Fields -->
                    <label for="pengguna">Pengguna:</label>
                    <input type="text" id="pengguna" name="pengguna" required />

                    <label for="lokasi">Lokasi:</label>
                    <input type="text" id="lokasi" name="lokasi" required />

                    <label for="volume_air_m3">Volume Air:</label>
                    <input type="text" id="volume_air_m3" name="volume_air_m3" required />

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

                    <label for="jumlah_pajak">Jumlah Pajak:</label>
                    <input type="number" id="jumlah_pajak" name="jumlah_pajak" required />

                    <label for="periode_pajak">Periode Pajak:</label>
                    <input type="date" id="periode_pajak" name="periode_pajak" required />

                    <label for="tanggal_jatuh_tempo">Tanggal Jatuh Tempo:</label>
                    <input type="date" id="tanggal_jatuh_tempo" name="tanggal_jatuh_tempo" required />

                    <label for="status_pembayaran">Status Pembayaran:</label>
                    <select id="status_pembayaran" name="status_pembayaran"> 
                        <option value="BELUM_DIBAYAR">Belum Dibayar</option>
                        <option value="DIBAYAR">Dibayar</option>
                    </select>

                    <label for="tanggal_pembayaran">Tanggal Pembayaran:</label>
                    <input type="date" id="tanggal_pembayaran" name="tanggal_pembayaran" />








                    <div class="form-buttons">
                        <button type="submit" class="submit-button">Simpan</button>
                    </div>
                </form>
            </div>

            <!-- Dialog message -->
            <div id="dialogOverlay" class="dialog-overlay">
                <div id="dialog" class="dialog">
                    <!-- Dialog content will be injected by JavaScript -->
                </div>
            </div>

            <%-- Proses Data Jika Form  --%>
            <%
                if ("POST".equalsIgnoreCase(request.getMethod())) {
                    try {
                        TaxWater taxWater = new TaxWater();
                        taxWater.setPengguna(request.getParameter("pengguna"));
                        taxWater.setLokasi(request.getParameter("lokasi"));
                        taxWater.setVolumeAirM3(Double.valueOf(request.getParameter("volume_air_m3")));
                        // Perbaikan di sini
                        // Ambil Nama Pajak dari form
                        String namaPajak = request.getParameter("namaPajak");

                        // Cari TaxType berdasarkan namaPajak
                        TaxType taxType = PstTaxType.fetchByName(namaPajak);
                        if (taxType == null) {
                            throw new Exception("Jenis pajak tidak ditemukan.");
                        }
                        taxWater.setTaxType(taxType);

                        // Pengecekan dan parsing jumlah pajak
                        String jumlahPajakStr = request.getParameter("jumlah_pajak");
                        if (jumlahPajakStr != null && !jumlahPajakStr.isEmpty()) {
                            taxWater.setJumlahPajak(Double.parseDouble(jumlahPajakStr));
                        } else {
                            throw new Exception("Jumlah pajak tidak boleh kosong.");
                        }

                        // Pengecekan dan parsing tanggal proses
                        String periodePajakStr = request.getParameter("periode_pajak");
                        if (periodePajakStr != null && !periodePajakStr.isEmpty()) {
                            taxWater.setPeriodePajak(java.sql.Date.valueOf(periodePajakStr));
                        } else {
                            throw new Exception("Tanggal periode tidak boleh kosong.");
                        }

                        // Pengecekan dan parsing tanggal jatuh tempo
                        String tanggalJatuhTempoStr = request.getParameter("tanggal_jatuh_tempo");
                        if (tanggalJatuhTempoStr != null && !tanggalJatuhTempoStr.isEmpty()) {
                            taxWater.setTanggalJatuhTempo(java.sql.Date.valueOf(tanggalJatuhTempoStr));
                        } else {
                            throw new Exception("Tanggal jatuh tempo tidak boleh kosong.");
                        }

                        // Status Pembayaran
                        String statusPembayaranStr = request.getParameter("status_pembayaran");
                        if (statusPembayaranStr != null && !statusPembayaranStr.isEmpty()) {
                            PaymentStatus statusPembayaran = PaymentStatus.valueOf(statusPembayaranStr.toUpperCase());
                            taxWater.setStatusPembayaran(statusPembayaran);
                        } else {
                            throw new Exception("Status pembayaran tidak boleh kosong.");
                        }

                        // Pengecekan dan parsing tanggal pembayaran (opsional)
                        String tanggalPembayaranStr = request.getParameter("tanggal_pembayaran");
                        if (tanggalPembayaranStr != null && !tanggalPembayaranStr.isEmpty()) {
                            taxWater.setTanggalPembayaran(java.sql.Date.valueOf(tanggalPembayaranStr));
                        }

                        // Buat instance Psttaxwater untuk memanggil metode insertExc()
                        PstTaxWater pstTaxWater = new PstTaxWater();

                        // Gunakan metode insertExc untuk memasukkan data ke database
                        pstTaxWater.insertExc(taxWater);
                        response.sendRedirect("list_tax_water.jsp");
                        
                        out.println("<p>Data berhasil disimpan!</p>");
                    } catch (NumberFormatException e) {
                        out.println("<p>Format jumlah pajak tidak valid: " + e.getMessage() + "</p>");
                    } catch (IllegalArgumentException e) {
                        out.println("<p>Format status pembayaran tidak valid: " + e.getMessage() + "</p>");
                    } catch (Exception e) {
                        out.println("<p>Terjadi kesalahan: " + e.getMessage() + "</p>");
                    }
                    
                }
            %>


            <!-- Existing table display logic for showing tax ownership data -->
            <table id="data-table">
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
                        <th>Aksi</th>

                    </tr>
                </thead>
                <tbody>
                    <% if (listTypes == null || listTypes.isEmpty()) { %>
                    <tr>
                        <td colspan="11" class="no-data">Tidak ada data kepemilikan pajak tersedia.</td>
                    </tr>
                    <% } else {
                        int index = 1;
                        for (TaxWater taxWater : listTypes) {
                            String taxTypeName = "";
                            try {
                                TaxType taxType = taxWater.getTaxType();
                                if (taxType != null) {
                                    taxTypeName = taxType.getNamaPajak();
                                }
                            } catch (Exception e) {
                                taxTypeName = "Unknown";
                            }
                    %>
                    <tr>
                        <td><%= index++%></td>
                        <td><%= taxWater.getPengguna()%></td>
                        <td><%= taxWater.getLokasi()%></td>
                        <td><%= taxWater.getVolumeAirM3()%></td>
                        <td><%= taxTypeName%></td>
                        <td><%= taxWater.getJumlahPajak()%></td>
                        <td><%= taxWater.getPeriodePajak()%></td>
                        <td><%= taxWater.getTanggalJatuhTempo()%></td>
                        <td><%= taxWater.getStatusPembayaran()%></td>
                        <td><%= taxWater.getTanggalPembayaran() != null ? taxWater.getTanggalPembayaran() : ""%></td>


                        
                        <td>


                           <a href="list_tax_water.jsp?idDeleteTax=<%= taxWater.getOID() %>"
                               class="delete-btn" onclick="return confirm('Apakah Anda yakin ingin menghapus data ini?');">Delete</a>
                            



                        </td>
                    </tr>
                    <% }
                        }%>
                </tbody>
            </table>
        </div>
        </div>
    </body>
</html>