<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <style>
            html, body {
                height: 100%;
                margin: 0;
            }

            body {
                font-family: 'Roboto', sans-serif;
                background-color: #f4f7f9;
                overflow: hidden; /* Mencegah overflow body */
            }

            .container {
                display: flex;
                height: 100vh;
                overflow: hidden; /* Mencegah overflow container */
            }

            .sidebar {
                width: 250px;
                background-color: #3a5ba0;
                color: white;
                padding: 20px;
                display: flex;
                flex-direction: column;
                box-shadow: 2px 0 5px rgba(0,0,0,0.1);
                overflow-y: auto; /* Scroll jika konten melebihi batas */
            }

            .sidebar h1 {
                font-size: 26px;
                margin-bottom: 30px;
                border-bottom: 2px solid white;
                padding-bottom: 10px;
                text-transform: uppercase;
                letter-spacing: 1px;
            }

            .sidebar h2 {
                font-size: 16px;
                margin: 15px 0;
                text-transform: uppercase;
                letter-spacing: 1px;
                color: #cfd8f4;
                cursor: pointer;
                position: relative;
            }

            .sidebar h2::after {
                content: '\25BC'; /* Segitiga ke bawah */
                font-size: 12px;
                color: #cfd8f4;
                position: absolute;
                right: 10px;
                transition: transform 0.3s ease;
            }

            .sidebar h2.active::after {
                transform: rotate(-180deg); /* Putar segitiga */
            }

            .sidebar .submenu {
                display: none;
                margin-left: 10px;
                padding-left: 10px;
            }

            .sidebar .submenu a {
                text-decoration: none;
                color: #cfd8f4;
                margin: 10px 0;
                display: block;
                font-size: 14px;
                padding: 8px 12px;
                border-radius: 4px;
                transition: background-color 0.3s, padding-left 0.3s;
            }

            .sidebar .submenu a:hover {
                background-color: #284d8b;
                padding-left: 20px;
                color: #fff;
            }

            .main-content-wrapper {
                flex-grow: 1;
                display: flex;
                overflow: hidden; /* Pastikan konten pas dalam wrapper */
            }

            .main-content {
                flex: 1;
                background-color: #fff;
                padding: 30px;
                overflow: auto;
                border-radius: 10px;
                transition: opacity 0.5s ease-in-out, transform 0.5s ease-in-out;
                opacity: 1;
                transform: translateX(0);
            }

            .main-content.fade-out {
                opacity: 0;
                transform: translateX(-10px);
            }

            .main-content.fade-in {
                opacity: 1;
                transform: translateX(0);
            }

            .main-content h1 {
                font-size: 24px;
                margin-bottom: 20px;
                color: #333;
                font-weight: 400;
            }

            .dashboard-cards {
                display: flex;
                justify-content: space-between;
                margin-bottom: 20px;
            }

            .card {
                background-color: #fff;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                border-radius: 10px;
                padding: 20px;
                width: 30%;
                text-align: center;
                min-width: 200px; /* Pastikan kartu tidak menyusut terlalu banyak */
            }

            .card h2 {
                font-size: 20px;
                margin-bottom: 10px;
                color: #426DAE;
            }

            .card p {
                font-size: 16px;
                color: #666;
            }

            .card-icon {
                font-size: 40px;
                color: #426DAE;
                margin-bottom: 10px;
            }

            .chart-container {
                background-color: #fff;
                padding: 20px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                border-radius: 10px;
            }

        </style>

        <!-- Google Font -->
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&display=swap" rel="stylesheet">

        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

        <!-- Chart.js (untuk grafik) -->
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

        <!-- Skrip Dashboard dan Konten Dinamis -->
        <script>
            $(document).ready(function () {
                // Fungsi untuk memuat halaman secara dinamis dengan transisi yang mulus
                function loadPage(page) {
                    var $currentContent = $('.main-content');
                    $currentContent.addClass('fade-out');

                    setTimeout(function () {
                        $currentContent.load(page, function () {
                            $currentContent.removeClass('fade-out').addClass('fade-in');
                            // Hapus kelas setelah transisi selesai
                            setTimeout(function () {
                                $currentContent.removeClass('fade-in');
                            }, 500); // Cocokkan durasi transisi fade-in

                            // Panggil fungsi pemuatan grafik jika halaman yang dimuat adalah dashboard.jsp
                            if (page === 'dashboard.jsp') {
                                loadDashboardChart(); // Pastikan fungsi ini dipanggil untuk dashboard
                            }
                        });
                    }, 300); // Cocokkan durasi transisi fade-out
                }

                // Toggel visibilitas submenu
                $('.sidebar h2').click(function () {
                    $(this).toggleClass('active');
                    $(this).next('.submenu').slideToggle();
                });

                // Ikat tautan sidebar untuk memuat konten secara dinamis
                $('.sidebar a').click(function (e) {
                    e.preventDefault();
                    var page = $(this).data('page');
                    loadPage(page);
                });
            });

            
        </script>
    </head>
    <body>
        <div class="container">
            <div class="sidebar">
                <h1>Pajak Manajemen</h1>
                
                <h2>Data Master</h2>
                <div class="submenu">
                    <a href="#" data-page="tax_vehicle/list_tax_vehicle.jsp">Pajak Kendaraan</a>
                    <a href="#" data-page="tax_ownership/list_tax_ownership.jsp">Pajak Transfer Kendaraan</a>
                    <a href="#" data-page="tax_fuel_sales/list_tax_fuel_sales.jsp">Pajak Bahan Bakar</a>
                    <a href="#" data-page="tax_water/list_tax_water.jsp">Pajak Air</a>
                    <a href="#" data-page="list_tax_cigarette/list_tax_cigarette.jsp">Pajak Rokok</a>
                </div>
            </div>
        </div> 
    </body> 
</html>