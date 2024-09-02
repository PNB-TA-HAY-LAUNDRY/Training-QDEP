<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Vector" %>
<%@ page import="com.dimata.entity.pajak.PstTaxPayment" %>
<%@ page import="com.dimata.entity.pajak.TaxPayment" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Daftar Pembayaran Pajak</title>
    <style>
        /* Styling umum */
        body {
            font-family: 'Comic Sans MS', sans-serif;
            background: linear-gradient(45deg, #ff9a9e 0%, #fecfef 99%, #fe99ff 100%);
            color: #333;
            margin: 0;
            padding: 0;
        }

        /* Header */
        header {
            background-color: #ff6f61;
            color: white;
            padding: 20px;
            text-align: center;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
        }

        h1 {
            margin: 0;
            font-size: 2.5em;
            text-transform: uppercase;
            letter-spacing: 2px;
            text-shadow: 2px 2px #d64f4f;
        }

        /* Konten utama */
        main {
            padding: 20px;
        }

        /* Tabel */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 30px;
        }

        table, th, td {
            border: 2px solid #ff6f61;
        }

        th, td {
            padding: 15px;
            text-align: center;
            background-color: #ffede0;
            color: #333;
            font-size: 1.1em;
        }

        th {
            background-color: #ff6f61;
            color: white;
            font-size: 1.3em;
            text-shadow: 1px 1px #d64f4f;
        }

        tr:nth-child(even) {
            background-color: #ffe8d1;
        }

        tr:hover {
            background-color: #ffd1a4;
        }

        /* Tombol Hapus */
        .delete-btn {
            background-color: #ff6f61;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            transition: background-color 0.3s ease;
            text-shadow: 1px 1px #d64f4f;
        }

        .delete-btn:hover {
            background-color: #ff5a4f;
        }

        /* Tombol Tambah */
        .btn {
            background-color: #28a745;
            color: white;
            padding: 10px 20px;
            border-radius: 5px;
            text-decoration: none;
            display: inline-block;
            margin-top: 20px;
            transition: background-color 0.3s ease;
        }

        .btn:hover {
            background-color: #218838;
        }

        /* Pesan */
        p {
            font-size: 1.2em;
            font-weight: bold;
            margin: 20px 0;
        }

        .success-message {
            background-color: #d4edda;
            color: #155724;
            border-left: 10px solid #28a745;
            padding: 15px;
            border-radius: 8px;
        }

        .error-message {
            background-color: #f8d7da;
            color: #721c24;
            border-left: 10px solid #dc3545;
            padding: 15px;
            border-radius: 8px;
        }

        /* No Records */
        .no-records {
            text-align: center;
            font-style: italic;
            color: #666;
        }

        /* Container untuk tombol */
        .btn-container {
            text-align: center;
        }
    </style>
</head>
<body>
    <header>
        <h1>Daftar Pembayaran Pajak</h1>
    </header>
    <main>
        <%
            // Menghapus data pembayaran pajak berdasarkan ID
            String deleteIdStr = request.getParameter("deleteId");
            if (deleteIdStr != null && !deleteIdStr.isEmpty()) {
                try {
                    long deleteId = Long.parseLong(deleteIdStr);
                    if (deleteId > 0) {
                        int result = PstTaxPayment.deleteById(deleteId);
                        if (result > 0) {
                            out.println("<p class='success-message'>Data pembayaran pajak dengan ID " + deleteId + " berhasil dihapus.</p>");
                        } else {
                            out.println("<p class='error-message'>Gagal menghapus data pembayaran pajak dengan ID " + deleteId + ".</p>");
                        }
                    } else {
                        out.println("<p class='error-message'>ID tidak valid.</p>");
                    }
                } catch (Exception e) {
                    out.println("<p class='error-message'>Error: " + e.getMessage() + "</p>");
                }
            }

            // Menampilkan daftar pembayaran pajak
            Vector<TaxPayment> taxPayments = PstTaxPayment.list(0, 100, null, "id_pembayaran_pajak DESC");
        %>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Total Pembayaran</th>
                    <th>Tanggal Pembayaran</th>
                    <th>Tagihan Pajak ID</th>
                    <th>Aksi</th>
                </tr>
            </thead>
            <tbody>
                <%
                    if (taxPayments != null && !taxPayments.isEmpty()) {
                        for (TaxPayment taxPayment : taxPayments) {
                %>
                <tr>
                    <td><%= taxPayment.getOID() %></td>
                    <td><%= taxPayment.getTotalPayment() %></td>
                    <td><%= taxPayment.getPaymentDate() %></td>
                    <td><%= taxPayment.getTaxBillId() %></td>
                    <td>
                        <a href="payment_tax_list.jsp?deleteId=<%= taxPayment.getOID() %>" class="delete-btn">Delete</a>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="5" class="no-records">No records found.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
        <div class="btn-container">
            <a href="add_tax_payment.jsp" class="btn">Add New Tax Payment</a>
        </div>
    </main>
</body>
</html>
