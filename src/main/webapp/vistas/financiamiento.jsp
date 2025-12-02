<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>GLOBAL CARS - Financiamiento</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">

    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>

<body class="d-flex flex-column min-vh-100">

    <!-- HEADER -->
    <jsp:include page="/includes/header.jsp" />

    <!-- HERO -->
    <section class="bg-primary text-white text-center py-5">
        <div class="container">
            <h1 class="display-4 fw-bold">Simulación de Financiamiento</h1>
            <p class="lead">Aquí verás tu plan de pagos con interés mensual del 3%.</p>
        </div>
    </section>

    <!-- SIMULADOR -->
    <section class="py-5 bg-light">
        <div class="container">
            <h2 class="mb-4 text-center">Detalle de tu financiamiento</h2>

            <div class="row justify-content-center">
                <div class="col-md-8">

                    <div class="card shadow-lg border-0">
                        <div class="card-body">
                            <form>

                                <div class="mb-3 text-start">
                                    <label class="form-label fw-semibold">Precio del carro (S/)</label>
                                    <input type="number" class="form-control" id="precio" placeholder="Ej. 45000">
                                </div>

                                <div class="mb-3 text-start">
                                    <label class="form-label fw-semibold">Número de cuotas</label>
                                    <input type="number" class="form-control" id="cuotas" placeholder="Ej. 24">
                                </div>

                                <button type="button" class="btn btn-success w-100 fw-bold"
                                        onclick="calcularTabla()">
                                    <i class="bi bi-table me-1"></i> Generar tabla de financiamiento
                                </button>

                                <div class="mt-4" id="resultado"></div>

                            </form>
                        </div>
                    </div>

                </div>
            </div>

        </div>
    </section>

    <!-- FOOTER -->
    <jsp:include page="/includes/footer.jsp" />

    <!-- SCRIPTS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        function calcularTabla() { console.log("calculo");

            const precio = parseFloat(document.getElementById('precio').value);
            const cuotas = parseInt(document.getElementById('cuotas').value);
            const resultado = document.getElementById('resultado');

            console.log("PRECIO:", precio, "CUOTAS:", cuotas);

            if (!precio || !cuotas || precio <= 0 || cuotas <= 0 || cuotas > 72) {
                resultado.innerHTML = `
                    <div class="alert alert-danger mt-3">
                        Por favor ingresa valores válidos (máximo 72 meses).
                    </div>`;
                return;
            }

            const interesMensual = 0.03;
            const i = interesMensual;
            const n = cuotas;

            const cuota = precio * (i * Math.pow(1 + i, n)) / (Math.pow(1 + i, n) - 1);

            let saldo = precio;
console.log(saldo);
            let tabla = `
                <div class="alert alert-info fw-semibold mt-3">
                    Tu cuota mensual aproximada es:
                    <span class="text-success">S/ ${cuota.toFixed(2)}</span>
                </div>

                <div class="table-responsive mt-3">
                    <table class="table table-striped table-bordered">
                        <thead class="table-success">
                            <tr>
                                <th>Mes</th>
                                <th>Cuota</th>
                                <th>Interés</th>
                                <th>Capital</th>
                                <th>Saldo restante</th>
                            </tr>
                        </thead>
                        <tbody>
            `;

let cuerpo="";
            for (let mes = 1; mes <= n; mes++) {

                const interes = saldo * i;
                const capital = cuota - interes;
                saldo -= capital;
console.log(cuota);
                cuerpo += "<tr>"+
                        "<td> "+mes+"</td>"+
                        "<td>S/ "+cuota.toFixed(2)+"</td>"+
                        "<td>S/ "+interes.toFixed(2)+"</td>"+
                        "<td>S/ "+capital.toFixed(2)+"</td>"+
                        "<td>S/ "+saldo.toFixed(2)+"</td>"+
                        
                      
                    "</tr> ";
              
            }
console.log(cuerpo);
           tabla += cuerpo+" </tbody></table></div>";
            resultado.innerHTML = tabla;
        }
    </script>

</body>
</html>
