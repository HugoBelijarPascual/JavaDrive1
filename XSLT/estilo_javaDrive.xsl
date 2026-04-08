<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="html" encoding="UTF-8" doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"/>

    <!-- ===== PLANTILLA PRINCIPAL ===== -->
    <xsl:template match="/">
        <html lang="es">
            <head>
                <meta charset="UTF-8"/>
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                <title>Sistema de Gestión JavaDrive</title>
                <style>
                    * { box-sizing: border-box; margin: 0; padding: 0; }

                    body {
                    font-family: 'Segoe UI', Arial, sans-serif;
                    background: #f4f6f9;
                    color: #2d2d2d;
                    padding: 32px 24px;
                    }

                    /* ---- Cabecera ---- */
                    .header {
                    background: linear-gradient(135deg, #1a1a2e 0%, #16213e 60%, #0f3460 100%);
                    color: #ffffff;
                    padding: 32px 40px;
                    border-radius: 12px;
                    margin-bottom: 32px;
                    display: flex;
                    align-items: center;
                    justify-content: space-between;
                    }
                    .header h1 {
                    font-size: 28px;
                    font-weight: 700;
                    letter-spacing: 1px;
                    }
                    .header h1 span {
                    color: #e94560;
                    }
                    .header .empresa-info {
                    font-size: 14px;
                    opacity: 0.8;
                    margin-top: 6px;
                    }
                    .header .badge {
                    background: #e94560;
                    color: #fff;
                    padding: 8px 18px;
                    border-radius: 20px;
                    font-size: 13px;
                    font-weight: 600;
                    }

                    /* ---- Secciones ---- */
                    .seccion {
                    background: #ffffff;
                    border-radius: 10px;
                    box-shadow: 0 2px 12px rgba(0,0,0,0.07);
                    margin-bottom: 28px;
                    overflow: hidden;
                    }
                    .seccion-titulo {
                    background: #0f3460;
                    color: #ffffff;
                    padding: 14px 24px;
                    font-size: 16px;
                    font-weight: 600;
                    letter-spacing: 0.5px;
                    display: flex;
                    align-items: center;
                    gap: 10px;
                    }
                    .seccion-titulo .icono {
                    font-size: 18px;
                    }

                    /* ---- Tabla ---- */
                    table {
                    width: 100%;
                    border-collapse: collapse;
                    }
                    thead tr {
                    background: #e8edf5;
                    }
                    thead th {
                    padding: 12px 20px;
                    text-align: left;
                    font-size: 12px;
                    font-weight: 700;
                    text-transform: uppercase;
                    letter-spacing: 0.8px;
                    color: #5a6a85;
                    border-bottom: 2px solid #d0d8e8;
                    }
                    tbody tr {
                    border-bottom: 1px solid #f0f2f5;
                    transition: background 0.15s;
                    }
                    tbody tr:hover {
                    background: #f7f9fc;
                    }
                    tbody td {
                    padding: 14px 20px;
                    font-size: 14px;
                    color: #3a3a3a;
                    }

                    /* ---- Badges de tipo ---- */
                    .badge-tipo {
                    display: inline-block;
                    padding: 4px 12px;
                    border-radius: 12px;
                    font-size: 12px;
                    font-weight: 700;
                    letter-spacing: 0.3px;
                    }
                    .badge-coche {
                    background: #dff0ff;
                    color: #0d6eac;
                    border: 1px solid #b3d9f5;
                    }
                    .badge-furgoneta {
                    background: #fff3de;
                    color: #a0620a;
                    border: 1px solid #f5d99b;
                    }

                    /* ---- Badge disponibilidad ---- */
                    .badge-disponible {
                    background: #d4edda;
                    color: #155724;
                    border: 1px solid #b8dacc;
                    display: inline-block;
                    padding: 3px 10px;
                    border-radius: 10px;
                    font-size: 12px;
                    font-weight: 600;
                    }
                    .badge-no-disponible {
                    background: #fde8e8;
                    color: #7b2323;
                    border: 1px solid #f5b8b8;
                    display: inline-block;
                    padding: 3px 10px;
                    border-radius: 10px;
                    font-size: 12px;
                    font-weight: 600;
                    }

                    /* ---- Pie de página ---- */
                    .footer {
                    text-align: center;
                    color: #8a92a6;
                    font-size: 12px;
                    margin-top: 16px;
                    padding: 16px;
                    }

                    /* ---- Stats bar ---- */
                    .stats-bar {
                    display: flex;
                    gap: 16px;
                    padding: 16px 24px;
                    background: #f8f9fc;
                    border-bottom: 1px solid #eaecf0;
                    flex-wrap: wrap;
                    }
                    .stat-item {
                    display: flex;
                    align-items: center;
                    gap: 8px;
                    font-size: 13px;
                    color: #5a6a85;
                    }
                    .stat-number {
                    font-weight: 700;
                    font-size: 18px;
                    color: #0f3460;
                    }
                </style>
            </head>
            <body>

                <!-- CABECERA -->
                <div class="header">
                    <div>
                        <h1>Sistema de Gestión <span>JavaDrive</span></h1>
                        <div class="empresa-info">
                            <xsl:value-of select="javadrive/empresa/nombre"/>
                            &#160;|&#160;
                            <xsl:value-of select="javadrive/empresa/ubicacion"/>
                        </div>
                    </div>
                    <div class="badge">Informe Corporativo</div>
                </div>

                <!-- SECCIÓN FLOTA -->
                <div class="seccion">
                    <div class="seccion-titulo">
                        <span class="icono"></span>
                        Inventario de Flota
                    </div>

                    <!-- Stats -->
                    <div class="stats-bar">
                        <div class="stat-item">
                            <span class="stat-number"><xsl:value-of select="count(javadrive/flota/vehiculo)"/></span>
                            <span>vehículos totales</span>
                        </div>
                        <div class="stat-item">
                            <span class="stat-number"><xsl:value-of select="count(javadrive/flota/vehiculo[@tipo='Coche'])"/></span>
                            <span>coches</span>
                        </div>
                        <div class="stat-item">
                            <span class="stat-number"><xsl:value-of select="count(javadrive/flota/vehiculo[@tipo='Furgoneta'])"/></span>
                            <span>furgonetas</span>
                        </div>
                    </div>

                    <table>
                        <thead>
                            <tr>
                                <th>Tipo</th>
                                <th>Matrícula</th>
                                <th>Marca y Modelo</th>
                                <th>Especificaciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <xsl:apply-templates select="javadrive/flota/vehiculo"/>
                        </tbody>
                    </table>
                </div>

                <!-- SECCIÓN CLIENTES -->
                <div class="seccion">
                    <div class="seccion-titulo">
                        <span class="icono"></span>
                        Listado de Clientes
                    </div>

                    <!-- Stats -->
                    <div class="stats-bar">
                        <div class="stat-item">
                            <span class="stat-number"><xsl:value-of select="count(javadrive/clientes/cliente)"/></span>
                            <span>clientes registrados</span>
                        </div>
                    </div>

                    <table>
                        <thead>
                            <tr>
                                <th>DNI</th>
                                <th>Nombre Completo</th>
                                <th>Teléfono</th>
                            </tr>
                        </thead>
                        <tbody>
                            <xsl:apply-templates select="javadrive/clientes/cliente"/>
                        </tbody>
                    </table>
                </div>

                <div class="footer">
                    Generado por JavaDrive &#8212; Sistema de Gestión de Alquiler
                </div>

            </body>
        </html>
    </xsl:template>

    <!-- ===== PLANTILLA VEHÍCULO ===== -->
    <xsl:template match="vehiculo">
        <tr>
            <td>
                <xsl:choose>
                    <xsl:when test="@tipo='Coche'">
                        <span class="badge-tipo badge-coche">Coche</span>
                    </xsl:when>
                    <xsl:otherwise>
                        <span class="badge-tipo badge-furgoneta">Furgoneta</span>
                    </xsl:otherwise>
                </xsl:choose>
            </td>
            <td><strong><xsl:value-of select="matricula"/></strong></td>
            <td><xsl:value-of select="marca"/> - <xsl:value-of select="modelo"/></td>
            <td><xsl:value-of select="especifico"/></td>
        </tr>
    </xsl:template>

    <!-- ===== PLANTILLA CLIENTE ===== -->
    <xsl:template match="cliente">
        <tr>
            <td><xsl:value-of select="dni"/></td>
            <td><xsl:value-of select="nombre"/></td>
            <td><xsl:value-of select="telefono"/></td>
        </tr>
    </xsl:template>

</xsl:stylesheet>
