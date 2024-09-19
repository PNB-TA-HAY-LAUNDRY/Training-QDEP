<%@page import="com.dimata.qdep.db.DBException"%>
<%@page import="com.dimata.entity.pajak.PstTaxOwnerships"%>
<%@page import="com.dimata.entity.pajak.PaymentStatus"%>
<%@page import="java.util.List"%>
<%@page import="com.dimata.entity.pajak.PstTaxType"%>
<%@page import="com.dimata.qdep.system.I_DBExceptionInfo"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.form.pajak.CtrlTaxOwnerships"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.entity.pajak.TaxOwnerships"%>
<%@page import="com.dimata.entity.pajak.TaxType"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String currentPage = "list_tax_ownership";
    Vector<TaxOwnerships> listTypes = new Vector();
    CtrlTaxOwnerships ctrlTaxOwnerships = new CtrlTaxOwnerships(request);
    boolean success = false;
    
// Mengambil parameter deleteId jika ada
    String deleteIdStr = request.getParameter("deleteId");
    if (deleteIdStr != null && !deleteIdStr.isEmpty()) {
        try {
            long deleteId = Long.parseLong(deleteIdStr);
            if (deleteId > 0) {
                int result = PstTaxOwnerships.deleteById(deleteId);
                if (result > 0) {
                    out.println("<p class='success-message'>Data pajak dengan ID " + deleteId + " berhasil dihapus.</p>");
                } else {
                    out.println("<p class='error-message'>Gagal menghapus data pajak dengan ID " + deleteId + ".</p>");
                }
            } else {
                out.println("<p class='error-message'>ID tidak valid.</p>");
            }
        } catch (DBException e) {
            out.println("<p class='error-message'>Gagal menghapus data pajak: " + e.getMessage() + "</p>");
        } catch (NumberFormatException e) {
            out.println("<p class='error-message'>ID tidak valid.</p>");
        }
    }

    // Always list data
    try {
        ctrlTaxOwnerships.action(Command.LIST, 0);
        listTypes = (Vector<TaxOwnerships>) request.getAttribute("taxOwnerships");
    } catch (Exception e) {
        log("Error retrieving data: " + e.getMessage());
    }

%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Daftar Kepemilikan Pajak</title>
        <style>
            /* Existing CSS styles */
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
                background-color: var(--primary-color);
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
                background-color: var(--primary-color);
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
        </style>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
            function toggleForm() {
                var formContainer = document.getElementById('formContainer');
                formContainer.style.display = (formContainer.style.display === 'none' || formContainer.style.display === '') ? 'block' : 'none';
            }
        </script>
     </head>
    <body>
        <!-- Include the sidebar -->
       <%@ include file="/sidebar.jsp" %>


        <!-- Main Content -->
        <div id="page-content-wrapper">
            <div class="main-content">
                <h1>Daftar Kepemilikan Pajak</h1>

                <!-- Button to toggle form display -->
                <button class="form-toggle-button" onclick="toggleForm()">Tambah Data Kepemilikan Pajak</button>

                <!-- Form container -->
                <div id="formContainer" class="form-container">
                    <form method="post">
                    <!-- Form Fields -->
                    <label for="noPlat">Nomor Plat:</label>
                    <input type="text" id="noPlat" name="noPlat" required />

                    <label for="namaPemilikLama">Nama Pemilik Lama:</label>
                    <input type="text" id="namaPemilikLama" name="namaPemilikLama" required />

                    <label for="namaPemilikBaru">Nama Pemilik Baru:</label>
                    <input type="text" id="namaPemilikBaru" name="namaPemilikBaru" required />

                    <label for="alamatBaru">Alamat Baru:</label>
                    <input type="text" id="alamatBaru" name="alamatBaru" required />

                    <label for="jenisPajak">Jenis Pajak:</label>
                    <select id="jenisPajak" name="jenisPajak">
                        <option value="pajak kendaraan bermotor">Pajak Kendaraan Bermotor</option>
                        <option value="bea balik nama kendaraan bermotor">BEA Balik Nama Kendaraan Bermotor</option>
                        <option value="pajak bahan bakar kendaraan bermotor">Pajak Bahan Bakar Kendaraan Bermotor</option>
                        <option value="pajak air permukaan">Pajak Air Permukaan</option>
                        <option value="pajak rokok">Pajak Rokok</option>
                    </select>

                    <label for="jumlahPajak">Jumlah Pajak:</label>
                    <input type="number" id="jumlahPajak" name="jumlahPajak" required />

                    <label for="tanggalProses">Tanggal Proses:</label>
                    <input type="date" id="tanggalProses" name="tanggalProses" required />

                    <label for="tanggalJatuhTempo">Tanggal Jatuh Tempo:</label>
                    <input type="date" id="tanggalJatuhTempo" name="tanggalJatuhTempo" required />

                    <label for="statusPembayaran">Status Pembayaran:</label>
                    <select id="statusPembayaran" name="statusPembayaran">
                        <option value="BELUM_DIBAYAR">Belum Dibayar</option>
                        <option value="DIBAYAR">Dibayar</option>
                    </select>

                    <label for="tanggalPembayaran">Tanggal Pembayaran:</label>
                    <input type="date" id="tanggalPembayaran" name="tanggalPembayaran" />

                    <div class="form-buttons">
                            <button type="submit" class="submit-button">Simpan</button>
                        </div>
                    </form>
                </div>
                
            <%-- Proses Data Jika Form Disubmit --%>
            <%                if ("POST".equalsIgnoreCase(request.getMethod())) {
                    try {
                        TaxOwnerships taxOwnerships = new TaxOwnerships();
                        taxOwnerships.setNoPlat(request.getParameter("noPlat"));
                        taxOwnerships.setNamaPemilikLama(request.getParameter("namaPemilikLama"));
                        taxOwnerships.setNamaPemilikBaru(request.getParameter("namaPemilikBaru"));
                        taxOwnerships.setAlamatBaru(request.getParameter("alamatBaru"));
                        taxOwnerships.setJenisPajak(request.getParameter("jenisPajak"));

                        // Pengecekan dan parsing jumlah pajak
                        String jumlahPajakStr = request.getParameter("jumlahPajak");
                        if (jumlahPajakStr != null && !jumlahPajakStr.isEmpty()) {
                            taxOwnerships.setJumlahPajak(Double.parseDouble(jumlahPajakStr));
                        } else {
                            throw new Exception("Jumlah pajak tidak boleh kosong.");
                        }

                        // Pengecekan dan parsing tanggal proses
                        String tanggalProsesStr = request.getParameter("tanggalProses");
                        if (tanggalProsesStr != null && !tanggalProsesStr.isEmpty()) {
                            taxOwnerships.setTanggalProses(java.sql.Date.valueOf(tanggalProsesStr));
                        } else {
                            throw new Exception("Tanggal proses tidak boleh kosong.");
                        }

                        // Pengecekan dan parsing tanggal jatuh tempo
                        String tanggalJatuhTempoStr = request.getParameter("tanggalJatuhTempo");
                        if (tanggalJatuhTempoStr != null && !tanggalJatuhTempoStr.isEmpty()) {
                            taxOwnerships.setTanggalJatuhTempo(java.sql.Date.valueOf(tanggalJatuhTempoStr));
                        } else {
                            throw new Exception("Tanggal jatuh tempo tidak boleh kosong.");
                        }

                        // Status Pembayaran
                        String statusPembayaranStr = request.getParameter("statusPembayaran");
                        if (statusPembayaranStr != null && !statusPembayaranStr.isEmpty()) {
                            PaymentStatus statusPembayaran = PaymentStatus.valueOf(statusPembayaranStr.toUpperCase());
                            taxOwnerships.setStatusPembayaran(statusPembayaran);
                        } else {
                            throw new Exception("Status pembayaran tidak boleh kosong.");
                        }

                        // Pengecekan dan parsing tanggal pembayaran (opsional)
                        String tanggalPembayaranStr = request.getParameter("tanggalPembayaran");
                        if (tanggalPembayaranStr != null && !tanggalPembayaranStr.isEmpty()) {
                            taxOwnerships.setTanggalPembayaran(java.sql.Date.valueOf(tanggalPembayaranStr));
                        }

                        // Buat instance PstTaxOwnership untuk memanggil metode insertExc()
                        PstTaxOwnerships pstTaxOwnerships = new PstTaxOwnerships();

                        // Gunakan metode insertExc untuk memasukkan data ke database
                        pstTaxOwnerships.insertExc(taxOwnerships);
                    } catch (NumberFormatException e) {
                        out.println("<p>Format jumlah pajak tidak valid: " + e.getMessage() + "</p>");
                    } catch (IllegalArgumentException e) {
                        out.println("<p>Format status pembayaran tidak valid: " + e.getMessage() + "</p>");
                    } catch (Exception e) {
                    }
                }
            %>

            <!-- Existing table display logic for showing tax ownership data -->
            <table id="data-table" border="1">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>Nomor Plat</th>
                        <th>Nama Pemilik Lama</th>
                        <th>Nama Pemilik Baru</th>
                        <th>Jenis Pajak</th>
                        <th>Jumlah Pajak</th>
                        <th>Status Pembayaran</th>
                        <th>Tanggal Proses</th>
                        <th>Tanggal Jatuh Tempo</th>
                        <th>Aksi</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        int index = 1;
                        if (listTypes != null && !listTypes.isEmpty()) {
                            for (TaxOwnerships taxOwnerships : listTypes) {%>
                    <tr>
                        <td><%= index++%></td>
                        <td><%= taxOwnerships.getNoPlat()%></td>
                        <td><%= taxOwnerships.getNamaPemilikLama()%></td>
                        <td><%= taxOwnerships.getNamaPemilikBaru()%></td>
                        <td><%= taxOwnerships.getJenisPajak()%></td>
                        <td><%= taxOwnerships.getJumlahPajak()%></td>
                        <td><%= taxOwnerships.getStatusPembayaran()%></td>
                        <td><%= taxOwnerships.getTanggalProses()%></td>
                        <td><%= taxOwnerships.getTanggalJatuhTempo()%></td>
                        <td>
                            <!-- Tombol Delete dengan parameter ID -->
                            <form method="post" action="">
                                <input type="hidden" name="deleteId" value="<%= taxOwnerships.getTransferTaxId()%>">
                                <button type="submit" class="delete-btn">Hapus</button>
                            </form>
                        </td>
                    </tr>
                    <% }
                    } else { %>
                    <tr>
                        <td colspan="9" class="no-data">Tidak ada data yang ditemukan.</td>
                    </tr>
                    <% }%>
                </tbody>
            </table>

        </div>
    </body>
</html>