Comando por consola para Actualizar

Invoke-RestMethod -Uri "http://localhost:8080/ProyDesWeb2/api/v1/carros/1" -Method PUT -ContentType "application/json" -Body '{"nombre": "Toyota Corolla XLE", "precio": 28000.00}'

Comando por consola para crear

Invoke-RestMethod -Uri "http://localhost:8080/ProyDesWeb2/api/v1/carros" -Method POST -ContentType "application/json" -Body '{"nombre": "Toyota Corolla", "descripcion": "Sedan economico 2024", "imagen": "corolla.jpg", "precio": 25000.00}'

comando por consola para eliminar

Invoke-RestMethod -Uri "http://localhost:8080/ProyDesWeb2/api/v1/carros/5" -Method DELETE
