<%-- 
    Document   : sidebar
    Created on : Sep 19, 2024, 3:13:25 PM
    Author     : ihsan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sidebar</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.min.css">
        <style>
            @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap');
body {
    font-family: 'Poppins', sans-serif;
    height: 100%;
    margin: 0;
    padding: 0;
    background-color: #f8f9fa;
}

/* Sidebar styles */
#sidebar-wrapper {
    position: fixed;
    top: 0;
    left: 0;
    width: 250px;
    height: 100vh;
    background-color: #3498db;
    color: #ffffff;
    padding-top: 20px;
    overflow-y: auto;
    transition: all 0.3s ease;
    z-index: 1000;
}

#sidebar-wrapper .sidebar-heading {
    padding: 20px;
    background-color: #43b3fe;
    font-size: 18px;
    font-weight: 600;
    color: #ffffff;
    margin-bottom: 30px;
}

#sidebar-wrapper .list-group-item {
    padding: 15px 20px;
    background-color: transparent;
    border: none;
    color: #ffffff;
    font-size: 16px;
    display: flex;
    align-items: center;
    transition: background-color 0.2s ease;
    text-decoration: none; /* Remove underline from links */
}

#sidebar-wrapper .list-group-item i {
    margin-right: 10px;
}

#sidebar-wrapper .list-group-item:hover {
    background-color: #43b3fe;
    color: #ffffff;
    cursor: pointer;
}

#sidebar-wrapper .list-group-item.active {
    background-color: #001122;
    color: #ffffff;
}

/* Dropdown menu styles */
.collapse {
    display: none;
}

.collapse.show {
    display: block;
}

#masterdataSubmenu a {
    padding-left: 40px;
    font-size: 14px;
    color: #d1e0e0; /* Adjust color if needed */
    text-decoration: none; /* Remove underline from dropdown links */
}

#masterdataSubmenu a:hover {
    color: #ffffff;
}

/* Page content styles */
#page-content-wrapper {
    margin-left: 250px;
    padding: 20px;
    transition: margin-left 0.3s ease;
}

        </style>
    </head>
    <body>
        <!-- Sidebar -->
        <div id="sidebar-wrapper">
            <div class="sidebar-heading">
                <i class="bi bi-cash-stack"></i>
                Tax Management
            </div>
            <div class="list-group list-group-flush">
                <a href="${pageContext.request.contextPath}/index.jsp" class="list-group-item list-group-item-action ${currentPage == 'dashboard' ? 'active' : ''}">
                    <i class="bi bi-house-fill"></i> Dashboard
                </a>
                <a href="${pageContext.request.contextPath}/masterdata/list_tax_types.jsp" class="list-group-item list-group-item-action ${currentPage == 'dashboard' ? 'active' : ''}">
                    <i class="bi bi-blockquote-left"></i> Jenis Pajak Daerah
                </a>

                <!-- Dropdown Menu -->
                <a href="#masterdataSubmenu" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action dropdown-toggle">
                    <i class="bi bi-folder"></i> Masterdata
                </a>
                <ul class="collapse list-unstyled" id="masterdataSubmenu">
                    <li>
                        <a href="${pageContext.request.contextPath}/tax_ownership/list_tax_ownerships.jsp" class="list-group-item list-group-item-action ${currentPage == 'list_tax_ownerships' ? 'active' : ''}">
                            Pajak Transfer Kendaraan
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/asset_condition/asset_condition.jsp" class="list-group-item list-group-item-action ${currentPage == 'asset_condition' ? 'active' : ''}">
                            Asset Condition
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/asset_type/asset_type.jsp" class="list-group-item list-group-item-action ${currentPage == 'asset_type' ? 'active' : ''}">
                            Asset Type
                        </a>
                    </li>
                </ul>
            </div>
        </div>

        <!-- Script for dropdown functionality -->
        <script>
            document.querySelectorAll('.dropdown-toggle').forEach(item => {
                item.addEventListener('click', function() {
                    const collapse = this.nextElementSibling;
                    collapse.classList.toggle('show');
                });
            });
        </script>
    </body>
</html>
