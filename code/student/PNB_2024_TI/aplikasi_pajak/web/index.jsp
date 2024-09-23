<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.min.css">
        <style>
            @import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900&display=swap');

            body {
                font-family: "Poppins", sans-serif;
                height: 100%;
                margin: 0;
                padding: 0;
                background-color: #f8f9fa;
            }

            /* Page content styles */
            #page-content-wrapper {
                margin-left: 250px;
                padding: 20px;
                transition: margin-left 0.3s ease;
            }

            .hero-section {
                background-color: #3498db;
                color: #ffffff;
                padding: 40px 20px;
                border-radius: 8px;
                margin-bottom: 20px;
            }

            .hero-section h1 {
                margin: 0;
                font-size: 2.5rem;
                font-weight: 700;
            }

            .hero-section p {
                font-size: 1.2rem;
                margin: 10px 0 20px;
            }

            .hero-section a {
                color: #ffffff;
                font-weight: 600;
                text-decoration: none;
                border: 2px solid #ffffff;
                padding: 10px 20px;
                border-radius: 5px;
                transition: background-color 0.3s, color 0.3s;
            }

            .hero-section a:hover {
                background-color: #ffffff;
                color: #3498db;
            }

            .card-container {
                display: flex;
                gap: 20px;
                flex-wrap: wrap;
            }

            .card {
                background-color: #ffffff;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                padding: 20px;
                flex: 1 1 calc(33% - 20px);
                margin-bottom: 20px;
                transition: background-color 0.3s, box-shadow 0.3s;
            }

            .card h3 {
                margin-top: 0;
                color: #3498db;
                transition: color 0.3s;
            }

            .card p {
                font-size: 1rem;
                color: #333333;
            }

            .card:hover {
                background-color: #3498db; /* Warna latar saat dihover */
                box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2); /* Efek bayangan lebih kuat saat hover */
            }

            .card:hover h3 {
                color: #ffffff; /* Warna teks judul saat dihover */
            }

            .card:hover p {
                color: #f1f1f1; /* Warna teks isi saat dihover */
            }


        </style>
    </head>
    <body>
        <%-- Menyertakan sidebar --%>
        <%@ include file="sidebar.jsp" %>

        <div id="page-content-wrapper">
            <div class="hero-section">
                <h1>Manajemen Pajak</h1>
                <p>Website Manajemen Pajak adalah platform yang dirancang untuk mempermudah pengelolaan, pemantauan berbagai jenis pajak dengan efisien dan terpadu.</p>
                <a href="/aplikasi_pajak/masterdata/list_tax_types.jsp">
                    Jenis Pajak <i class="fas fa-arrow-right"></i>
                </a>
            </div>
            <div class="card-container">
                <div class="card">
                    <h3>Jenis Pajak Daerah</h3>
                    <p>Tabel Yang Berisi Jenis Pajak Dan Harga Pajak Pada suatu Daerah.</p>
                </div>
                <div class="card">
                    <h3>Mengelola Beberapa Pajak</h3>
                    <p>Tabel Yang Berisi Beberapa Nama Wajib Pajak.</p>
                </div>
                <div class="card">
                    <h3>Pajak Daerah</h3>
                    <p>Pajak daerah adalah pungutan wajib yang dikenakan oleh pemerintah daerah kepada warga atau badan usaha di wilayahnya untuk mendukung pembiayaan layanan publik dan pembangunan lokal.</p>
                </div>
            </div>
        </div>
    </body>
</html>