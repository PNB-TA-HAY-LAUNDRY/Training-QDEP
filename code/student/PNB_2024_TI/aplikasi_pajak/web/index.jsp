<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            color: #333;
            margin: 0;
            padding: 0;
            text-align: center;
        }
        h1 {
            color: #4CAF50;
            margin-top: 50px;
        }
        a {
            text-decoration: none;
            color: #fff;
            background-color: #007BFF;
            padding: 10px 20px;
            border-radius: 5px;
            margin: 10px;
            display: inline-block;
            transition: background-color 0.3s;
        }
        a:hover {
            background-color: #0056b3;
        }
        .container {
            margin: 0 auto;
            padding: 20px;
            max-width: 800px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Selamat Datang!</h1>
        <div>
            <a href="masterdata/list_tax_types.jsp">Jenis Pajak</a>
            <a href="masterdata/regional_tax_list.jsp">Pajak Daerah</a>
            <a href="masterdata/period_tax_list.jsp">periode pajak</a>
            <a href="masterdata/bill_tax_list.jsp">Tagihan Pajak</a>
            <a href="masterdata/payment_tax_list.jsp">Pembayaran Pajak</a>
        </div>
    </div>
</body>
</html>