<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tax Management - Dashboard</title>
    <style>
        html, body {
            height: 100%;
            margin: 0;
        }

        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f4f7f9;
            overflow: hidden; /* Prevent body overflow */
        }

        .container {
            display: flex;
            height: 100vh;
            overflow: hidden; /* Prevent container overflow */
        }

        .sidebar {
            width: 250px;
            background-color: #3a5ba0;
            color: white;
            padding: 20px;
            display: flex;
            flex-direction: column;
            box-shadow: 2px 0 5px rgba(0,0,0,0.1);
            overflow-y: auto; /* Scroll if content overflows */
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
            content: '\25BC'; /* Downward triangle */
            font-size: 12px;
            color: #cfd8f4;
            position: absolute;
            right: 10px;
            transition: transform 0.3s ease;
        }

        .sidebar h2.active::after {
            transform: rotate(-180deg); /* Rotate the triangle */
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
            overflow: hidden; /* Ensure content fits within the wrapper */
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
            min-width: 200px; /* Ensure cards don’t shrink too much */
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

    <!-- Chart.js (for charts/graphs) -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <!-- Dashboard and Dynamic Content Script -->
    <script>
        $(document).ready(function() {
            // Load the dashboard by default on page load
            loadPage('dashboard.jsp');

            // Function to load pages dynamically with smooth transitions
            function loadPage(page) {
                var $currentContent = $('.main-content');
                $currentContent.addClass('fade-out');

                setTimeout(function() {
                    $currentContent.load(page, function() {
                        $currentContent.removeClass('fade-out').addClass('fade-in');
                        // Remove the class after the transition ends
                        setTimeout(function() {
                            $currentContent.removeClass('fade-in');
                        }, 500); // Matches the duration of the fade-in transition

                        // Call chart loading function if the loaded page is dashboard.jsp
                        if (page === 'dashboard.jsp') {
                            loadDashboardChart(); // Ensure this function is called for the dashboard
                        }
                    });
                }, 300); // Matches the duration of the fade-out transition
            }

            // Toggle submenu visibility
            $('.sidebar h2').click(function() {
                $(this).toggleClass('active');
                $(this).next('.submenu').slideToggle();
            });

            // Bind sidebar links to load content dynamically
            $('.sidebar a').click(function(e) {
                e.preventDefault();
                var page = $(this).data('page');
                loadPage(page);
            });
        });

        // Function to load and display a chart
        function loadDashboardChart() {
            var ctx = document.getElementById('myChart').getContext('2d');
            new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
                    datasets: [{
                        label: '# of Votes',
                        data: [12, 19, 3, 5, 2, 3],
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(255, 159, 64, 0.2)'
                        ],
                        borderColor: [
                            'rgba(255, 99, 132, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        }
    </script>
</head>
<body>
    <div class="container">
        <div class="sidebar">
            <h1>Tax Management</h1>
            <h2>Menu</h2>
            <div class="submenu">
                <a href="#" data-page="dashboard.jsp">Dashboard</a>
                <a href="#" data-page="masterdata/list_tax_types.jsp">Regional Tax Types</a>
            </div>
            <h2>Master Data</h2>
            <div class="submenu">
                <a href="#" data-page="pajak_kendaraan.jsp">Vehicle Tax</a>
                <a href="#" data-page="pajak_balik_nama.jsp">Vehicle Transfer Tax</a>
                <a href="#" data-page="pajak_bahan_bakar.jsp">Fuel Tax</a>
                <a href="#" data-page="pajak_air.jsp">Water Tax</a>
            </div>
        </div>
        <div class="main-content-wrapper">
            <div class="main-content">
                <!-- Dynamic content will be loaded here -->
                <div class="chart-container">
                    <canvas id="myChart"></canvas>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
