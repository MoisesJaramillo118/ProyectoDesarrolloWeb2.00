<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container py-5 bg-light rounded">
    <h2 class="text-center mb-4">Modelos de Carros Disponibles</h2>

    <c:choose>
        <c:when test="${not empty carros}">
            <div id="carroCarrusel" class="carousel slide mb-5" data-bs-ride="carousel">
                
                <!-- Indicadores -->
                <div class="carousel-indicators mb-0">
                    <c:forEach var="carro" items="${carros}" varStatus="estado">
                        <button type="button"
                                data-bs-target="#carroCarrusel"
                                data-bs-slide-to="${estado.index}"
                                class="${estado.first ? 'active' : ''}"
                                aria-current="${estado.first ? 'true' : 'false'}"
                                aria-label="Slide ${estado.index + 1}"></button>
                    </c:forEach>
                </div>

                <!-- Slides -->
                <div class="carousel-inner">
                    <c:forEach var="carro" items="${carros}" varStatus="estado">
                        <div class="carousel-item ${estado.first ? 'active' : ''}">
                            <div class="d-flex justify-content-center">
                                <div class="card shadow-lg border-0" style="max-width: 400px;">
                                    <img src="${pageContext.request.contextPath}/img/${carro.imagen}"
                                         class="card-img-top"
                                         alt="${carro.nombre}">
                                    <div class="card-body text-center">
                                        <h5 class="card-title">${carro.nombre}</h5>
                                        <p class="card-text">${carro.descripcion}</p>
                                        <p class="card-text fw-bold text-primary">S/. ${carro.precio}</p>
                                        <a href="detalle-carro.jsp?id=${carro.id}" class="btn btn-outline-primary mt-2">Ver más</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <!-- Controles -->
                <button class="carousel-control-prev" type="button" data-bs-target="#carroCarrusel" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon bg-dark rounded-circle p-2" aria-hidden="true"></span>
                    <span class="visually-hidden">Anterior</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carroCarrusel" data-bs-slide="next">
                    <span class="carousel-control-next-icon bg-dark rounded-circle p-2" aria-hidden="true"></span>
                    <span class="visually-hidden">Siguiente</span>
                </button>
            </div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-warning text-center mt-4">No hay carros disponibles en este momento.</div>
        </c:otherwise>
    </c:choose>
</div>