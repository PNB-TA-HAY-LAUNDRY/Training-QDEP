<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" text/html; charset=UTF-8">
    <title>JSP Page</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            color: #333;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
            background: none;
        }
        header {
            padding: 30px;
            color: #333;
            text-align: center;
            margin-bottom: 50px;
        }
        h1 {
            margin: 0;
            font-size: 2.5em;
            font-weight: 500;
        }
        p.subtitle {
            font-size: 1.2em;
            color: #666;
            margin-top: 10px;
        }
        .button-container {
            margin-top: 30px;
        }
        a {
            text-decoration: none;
            color: #fff;
            background-color: #007BFF;
            padding: 12px 25px;
            border-radius: 50px;
            margin: 10px;
            display: inline-block;
            font-weight: 500;
            transition: background-color 0.3s, transform 0.3s;
            box-shadow: 0 4px 10px rgba(0, 123, 255, 0.3);
        }
        a:hover {
            background-color: #0056b3;
            transform: translateY(-2px);
            box-shadow: 0 6px 14px rgba(0, 123, 255, 0.4);
        }
        .container {
            max-width: 800px;
            padding: 20px;
            text-align: center;
        }
        @media (max-width: 600px) {
            header {
                padding: 20px;
            }
            h1 {
                font-size: 2em;
            }
            p.subtitle {
                font-size: 1em;
            }
            a {
                padding: 10px 20px;
                font-size: 0.9em;
            }
        }
    </style>
</head>
<body>
    <header>
        <h1>SELAMAT DATANG</h1>
        <p class="subtitle">Silahkan Pilih Menu Tabel Perpajakan Dibawah Ini!</p>
        <div class="button-container">
            <a href="masterdata/list_tax_types.jsp">Jenis Pajak</a>
            <a href="masterdata/regional_tax_list.jsp">Pajak Daerah</a>
            <a href="masterdata/period_tax_list.jsp">Periode Pajak</a>
            <a href="masterdata/bill_tax_list.jsp">Tagihan Pajak</a>
            <a href="masterdata/payment_tax_list.jsp">Pembayaran Pajak</a>
        </div>
    </header>
    <div class="container">
        <!-- Konten tambahan dapat ditempatkan di sini -->
    </div>
</body>
</html>
