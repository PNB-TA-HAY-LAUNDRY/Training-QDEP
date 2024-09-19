<%@page import="com.dimata.entity.pajak.PstTaxOwnership"%>
<%@page import="com.dimata.entity.pajak.PaymentStatus"%>
<%@page import="java.util.List"%>
<%@page import="com.dimata.entity.pajak.PstTaxType"%>
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
    // Ambil idDeleteTax dari request
long idDeleteTax = FRMQueryString.requestLong(request, "idDeleteTax");
CtrlTaxOwnership ctrlTaxOwnership = new CtrlTaxOwnership(request);
boolean success = false;

// Debug log untuk memastikan parameter diterima dengan benar
out.println("Received ID for delete: " + idDeleteTax);

// Menangani perintah DELETE
if (idDeleteTax > 0) {
    try {
        // Panggil metode actionDelete dengan idDeleteTax
        int action = ctrlTaxOwnership.actionDelete(idDeleteTax);
        if (action == I_DBExceptionInfo.NO_EXCEPTION) {
            success = true;
            out.println("Data berhasil dihapus.");
        } else {
            out.println("Gagal menghapus data: Kode error " + action);
        }
    } catch (Exception e) {
        out.println("Error saat menghapus data: " + e.getMessage());
    }
} else {
    out.println("ID tidak valid untuk penghapusan.");
}

// Menampilkan hasil aksi penghapusan
if (success) {
    // Tampilkan pesan sukses atau logika lain di halaman yang sama
    out.println("<p>Penghapusan berhasil.</p>");
} else {
    // Tampilkan pesan kesalahan di halaman yang sama
    out.println("<p>Penghapusan gagal. Harap periksa kembali.</p>");
}



    // Always list data
    try {
        ctrlTaxOwnership.action(Command.LIST, 0);
        listTypes = (Vector<TaxOwnership>) request.getAttribute("taxOwnerships");
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

            <!-- Dialog message -->
            <div id="dialogOverlay" class="dialog-overlay">
                <div id="dialog" class="dialog">
                    <!-- Dialog content will be injected by JavaScript -->
                </div>
            </div>

            <%-- Proses Data Jika Form Disubmit --%>
            <%
                if ("POST".equalsIgnoreCase(request.getMethod())) {
                    try {
                        TaxOwnership taxOwnership = new TaxOwnership();
                        taxOwnership.setNoPlat(request.getParameter("noPlat"));
                        taxOwnership.setNamaPemilikLama(request.getParameter("namaPemilikLama"));
                        taxOwnership.setNamaPemilikBaru(request.getParameter("namaPemilikBaru"));
                        taxOwnership.setAlamatBaru(request.getParameter("alamatBaru"));

                        // Ambil Nama Pajak dari form
                        String namaPajak = request.getParameter("namaPajak");

                        // Cari TaxType berdasarkan namaPajak
                        TaxType taxType = PstTaxType.fetchByName(namaPajak);
                        if (taxType == null) {
                            throw new Exception("Jenis pajak tidak ditemukan.");
                        }
                        taxOwnership.setTaxType(taxType);

                        // Pengecekan dan parsing jumlah pajak
                        String jumlahPajakStr = request.getParameter("jumlahPajak");
                        if (jumlahPajakStr != null && !jumlahPajakStr.isEmpty()) {
                            taxOwnership.setJumlahPajak(Double.parseDouble(jumlahPajakStr));
                        } else {
                            throw new Exception("Jumlah pajak tidak boleh kosong.");
                        }

                        // Pengecekan dan parsing tanggal proses
                        String tanggalProsesStr = request.getParameter("tanggalProses");
                        if (tanggalProsesStr != null && !tanggalProsesStr.isEmpty()) {
                            taxOwnership.setTanggalProses(java.sql.Date.valueOf(tanggalProsesStr));
                        } else {
                            throw new Exception("Tanggal proses tidak boleh kosong.");
                        }

                        // Pengecekan dan parsing tanggal jatuh tempo
                        String tanggalJatuhTempoStr = request.getParameter("tanggalJatuhTempo");
                        if (tanggalJatuhTempoStr != null && !tanggalJatuhTempoStr.isEmpty()) {
                            taxOwnership.setTanggalJatuhTempo(java.sql.Date.valueOf(tanggalJatuhTempoStr));
                        } else {
                            throw new Exception("Tanggal jatuh tempo tidak boleh kosong.");
                        }

                        // Status Pembayaran
                        String statusPembayaranStr = request.getParameter("statusPembayaran");
                        if (statusPembayaranStr != null && !statusPembayaranStr.isEmpty()) {
                            PaymentStatus statusPembayaran = PaymentStatus.valueOf(statusPembayaranStr.toUpperCase());
                            taxOwnership.setStatusPembayaran(statusPembayaran);
                        } else {
                            throw new Exception("Status pembayaran tidak boleh kosong.");
                        }

                        // Pengecekan dan parsing tanggal pembayaran (opsional)
                        String tanggalPembayaranStr = request.getParameter("tanggalPembayaran");
                        if (tanggalPembayaranStr != null && !tanggalPembayaranStr.isEmpty()) {
                            taxOwnership.setTanggalPembayaran(java.sql.Date.valueOf(tanggalPembayaranStr));
                        }

                        // Buat instance PstTaxOwnership untuk memanggil metode insertExc()
                        PstTaxOwnership pstTaxOwnership = new PstTaxOwnership();

                        // Gunakan metode insertExc untuk memasukkan data ke database
                        pstTaxOwnership.insertExc(taxOwnership);
                    } catch (NumberFormatException e) {
                        out.println("<p>Format jumlah pajak tidak valid: " + e.getMessage() + "</p>");
                    } catch (IllegalArgumentException e) {
                        out.println("<p>Format status pembayaran tidak valid: " + e.getMessage() + "</p>");
                    } catch (Exception e) {
                    }
                }
            %>

            <!-- Existing table display logic for showing tax ownership data -->
            <table id="data-table">
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
                        <td>
                            <a href="list_tax_ownership.jsp?idDeleteTax=<%= taxOwnership.getTransferTaxId()%>" 
                               onclick="return confirm('Apakah Anda yakin ingin menghapus data ini?');">Delete</a>

                        </td>
                    </tr>
                    <% }
                        }%>
                </tbody>
            </table>
        </div>
    </body>
</html>