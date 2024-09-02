<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Vector" %>
<%@ page import="com.dimata.entity.pajak.PstTaxPayment" %>
<%@ page import="com.dimata.entity.pajak.TaxPayment" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Daftar Pembayaran</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h1>Daftar Pembayaran</h1>

    <% 
        // Menangani penghapusan
        String deleteIdParam = request.getParameter("deleteId");
        if (deleteIdParam != null) {
            try {
                long deleteId = Long.parseLong(deleteIdParam);
                // Panggil metode hapus di PstTaxPayment
                PstTaxPayment.deleteById(deleteId);
                // Setelah penghapusan, arahkan ulang ke halaman yang sama
                response.sendRedirect("payment_tax_list.jsp");
                return; // Pastikan kode berikutnya tidak dieksekusi setelah redirect
            } catch (Exception e) {
                e.printStackTrace();
                out.println("Error deleting payment: " + e.getMessage());
            }
        }
    %>

    <table>
        <tr>
            <th>ID</th>
            <th>Total Payment</th>
            <th>Payment Date</th>
            <th>Tax Bill ID</th>
            <th>Aksi</th>
        </tr>
        <%
            // Initialize variables
            int limitStart = 0;
            int recordToGet = 10; // Example limit, you can change this as needed
            String whereClause = "";
            String order = "id_pembayaran_pajak ASC";
            
            // Fetch the list of tax payments
            Vector<TaxPayment> paymentList = PstTaxPayment.list(limitStart, recordToGet, whereClause, order);
            
            // Loop through the payment list and display each payment
            for (TaxPayment payment : paymentList) {
        %>
        <tr>
            <td><%= payment.getOID() %></td>
            <td><%= payment.getTotalPayment() %></td>
            <td><%= payment.getPaymentDate() %></td>
            <td><%= payment.getTaxBillId() %></td>
            <td>
                <!-- Link untuk menghapus data -->
                <a href="payment_tax_list.jsp?deleteId=<%= payment.getOID() %>" onclick="return confirm('Apakah Anda yakin ingin menghapus?')">Hapus</a>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</body>
</html>
