<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div>
    <h1>Dashboard</h1>
    <div class="dashboard-cards">
        <div class="card">
            <div class="card-icon">&#128178;</div>
            <h2>Total Pajak</h2>
            <p>Rp 1.200.000.000</p>
        </div>
        <div class="card">
            <div class="card-icon">&#128200;</div>
            <h2>Penerimaan Bulanan</h2>
            <p>Rp 250.000.000</p>
        </div>
        <div class="card">
            <div class="card-icon">&#128178;</div>
            <h2>Jumlah WP</h2>
            <p>1.500 WP Terdaftar</p>
        </div>
    </div>

    <div class="chart-container">
        <h2>Diagram Penerimaan Pajak</h2>
        <canvas id="myChart" width="400" height="150"></canvas>
    </div>
</div>
