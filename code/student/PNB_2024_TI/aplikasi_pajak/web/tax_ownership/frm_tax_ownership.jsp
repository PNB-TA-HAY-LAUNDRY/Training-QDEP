<%-- 
    Document   : frm_tax_ownership
    Created on : Sep 12, 2024, 11:34:48?AM
    Author     : ihsan
--%>
<%@page import="java.util.logging.Logger"%>
<%@page import="com.dimata.qdep.system.I_DBExceptionInfo"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.entity.pajak.TaxOwnership"%>
<%@page import="com.dimata.form.pajak.FrmTaxOwnership"%>
<%@page import="com.dimata.form.pajak.CtrlTaxOwnership"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    int iCommand = FRMQueryString.requestCommand(request);
    long taxOID = FRMQueryString.requestLong(request, "tax_oid");
    boolean success = false;
    CtrlTaxOwnership ctrlTaxOwnership = new CtrlTaxOwnership(request);
    FrmTaxOwnership frmTaxOwnership = new FrmTaxOwnership();
    String titlePage = "";
    Logger logger = Logger.getLogger("TaxOwnershipLogger");
    
    Vector<TaxOwnership> listOwnership = new Vector();
    TaxOwnership taxOwnership = new TaxOwnership();
    
    try {
        if (taxOID != 0) {
            titlePage = "Edit TaxOwnership";
            
            int action = ctrlTaxOwnership.action(iCommand, taxOID);
            logger.info("Received iCommand: " + iCommand);

            if (iCommand == Command.EDIT) {
                taxOwnership = (TaxOwnership) request.getAttribute("taxOwnership");
                logger.info("TaxOwnership: " + taxOwnership);
            }

            if (action == I_DBExceptionInfo.NO_EXCEPTION) {
                success = true;
            }
            
        } else {
            titlePage = "Add TaxOwnership";
            
            int action = ctrlTaxOwnership.action(iCommand, taxOID);
            if (action == I_DBExceptionInfo.NO_EXCEPTION) {
                success = true;
            }
        }
    } catch (Exception e) {
        logger.severe("Error exception: " + e.getMessage());
        e.printStackTrace();
    }

%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Form Kepemilikan Pajak</title>
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
            max-width: 600px;
        }

        .main-content h1 {
            font-size: 24px;
            margin-bottom: 20px;
            color: var(--text-color);
        }

        form {
            display: flex;
            flex-direction: column;
        }

        label {
            margin-bottom: 5px;
            font-weight: bold;
            color: var(--text-color);
        }

        input, select {
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid var(--border-color);
            border-radius: 4px;
            font-size: 14px;
        }

        button {
            padding: 10px;
            background-color: var(--primary-color);
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }

        button:hover {
            background-color: var(--secondary-color);
        }

        .error-message {
            color: red;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <div id="wrapper">
        <h1>Form Kepemilikan Pajak</h1>
        <div class="container">
        <!-- Form for adding and changing asset lists and handling command -->
        <form name="form" method="post" role="form">
            <label for="no_plat">No Plat:</label>
            <input type="text" id="no_plat" name="no_plat" required>

            <label for="nama_pemilik_lama">Nama Pemilik Lama:</label>
            <input type="text" id="nama_pemilik_lama" name="nama_pemilik_lama" required>

            <label for="nama_pemilik_baru">Nama Pemilik Baru:</label>
            <input type="text" id="nama_pemilik_baru" name="nama_pemilik_baru" required>

            <label for="alamat_baru">Alamat Baru:</label>
            <input type="text" id="alamat_baru" name="alamat_baru" required>

            <label for="jenis_pajak">Jenis Pajak:</label>
            <select id="jenis_pajak" name="jenis_pajak" required>
                <option value="">Pilih Jenis Pajak</option>
            </select>

            <label for="jumlah_pajak">Jumlah Pajak:</label>
            <input type="number" id="jumlah_pajak" name="jumlah_pajak" required>

            <label for="tanggal_proses">Tanggal Proses:</label>
            <input type="date" id="tanggal_proses" name="tanggal_proses" required>

            <label for="tanggal_jatuh_tempo">Tanggal Jatuh Tempo:</label>
            <input type="date" id="tanggal_jatuh_tempo" name="tanggal_jatuh_tempo" required>

            <label for="status_pembayaran">Status Pembayaran:</label>
            <select id="status_pembayaran" name="status_pembayaran" required>
                <option value="Belum Bayar">Belum Dibayar</option>
                <option value="Sudah Bayar">Dibayar</option>
            </select>

            <label for="tanggal_pembayaran">Tanggal Pembayaran (opsional):</label>
            <input type="date" id="tanggal_pembayaran" name="tanggal_pembayaran">
            
            <button type="submit">Simpan</button>
        </form>
    </div>
</body>

</html>