<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.entity.pajak.TaxType"%>
<%@page import="com.dimata.entity.pajak.PstTaxType"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Daftar Tipe Pajak</title>
    <style>
        /* Ensure the page layout adapts to sidebar and main content */
        html, body {
            height: 100%;
            margin: 0;
            font-family: 'Roboto', sans-serif;
            background-color: #f4f7f9;
        }

        .container {
            display: flex;
            min-height: 100vh;
            overflow: hidden;
        }

        .main-content-wrapper {
            flex-grow: 1;
            padding: 20px;
            box-sizing: border-box;
            margin-left: 250px; /* Match the sidebar width */
        }

        .main-content {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            overflow-x: auto;
        }

        .main-content h1 {
            font-size: 24px;
            margin-bottom: 20px;
            color: #333;
            font-weight: 400;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        table th, table td {
            padding: 15px;
            text-align: left;
            border: 1px solid #ddd;
        }

        th {
            background-color: #3a5ba0;
            color: white;
        }

        td {
            background-color: #f9f9f9;
        }

        tr:nth-child(even) td {
            background-color: #f1f1f1;
        }

        tr:hover td {
            background-color: #ececec;
        }

        .no-data {
            text-align: center;
            color: #666;
            padding: 20px;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        @media (max-width: 768px) {
            .main-content-wrapper {
                margin-left: 0; /* Collapse sidebar for smaller screens */
                padding: 10px;
            }

            table, th, td {
                font-size: 12px;
            }

            th, td {
                padding: 10px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Include the sidebar -->
         <%@ include file="/sidebar.jsp" %>

        <div class="main-content-wrapper">
            <div class="main-content">
                <h1>Daftar Pajak Daerah Provinsi Bali</h1>
                <table>
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Nama Pajak</th>
                            <th>Deskripsi</th>
                            <th>Tarif</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            // Mendapatkan data dari database menggunakan PstTaxType
                            Vector taxTypeList = PstTaxType.listAll(0, 0, null, "tax_type_id ASC");

                            if (taxTypeList != null && taxTypeList.size() > 0) {
                                // Iterasi melalui daftar tipe pajak
                                for (int i = 0; i < taxTypeList.size(); i++) {
                                    TaxType taxType = (TaxType) taxTypeList.get(i);
                        %>
                        <tr>
                            <td><%= i + 1 %></td> <!-- Menampilkan nomor urut -->
                            <td><%= taxType.getNamaPajak() %></td>
                            <td><%= taxType.getDeskripsi() %></td>
                            <td><%= taxType.getTarif() %></td>
                        </tr>
                        <%
                                }
                            } else {
                        %>
                        <tr>
                            <td colspan="4" class="no-data">Tidak ada data ditemukan</td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
