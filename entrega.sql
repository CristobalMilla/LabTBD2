-- Crear tablas principales para el sistema de delivery
CREATE TABLE clientes (
    cliente_id SERIAL PRIMARY KEY,
    nombre VARCHAR(100),
    direccion TEXT,
    email VARCHAR(100),
    telefono VARCHAR(20)
);

CREATE TABLE repartidores (
    repartidor_id SERIAL PRIMARY KEY,
    nombre VARCHAR(100),
    telefono VARCHAR(20),
    disponible BOOLEAN DEFAULT true
);

CREATE TABLE empresas (
    empresa_id SERIAL PRIMARY KEY,
    nombre VARCHAR(100),
    direccion TEXT,
    tipo_servicio VARCHAR(50)
);

CREATE TABLE productos (
    producto_id SERIAL PRIMARY KEY,
    empresa_id INT REFERENCES empresas(empresa_id),
    nombre VARCHAR(100),
    descripcion TEXT,
    precio DECIMAL(10,2),
    requiere_receta BOOLEAN DEFAULT false,
    categoria VARCHAR(100) 
);

CREATE TABLE pedidos (
    pedido_id SERIAL PRIMARY KEY,
    cliente_id INT REFERENCES clientes(cliente_id),
    empresa_id INT REFERENCES empresas(empresa_id),
    repartidor_id INT REFERENCES repartidores(repartidor_id),
    fecha TIMESTAMP,
    estado VARCHAR(50)
);

CREATE TABLE detalle_pedidos (
    detalle_id SERIAL PRIMARY KEY,
    pedido_id INT REFERENCES pedidos(pedido_id),
    producto_id INT REFERENCES productos(producto_id),
    cantidad INT
);

CREATE TABLE medios_pago (
    medio_id SERIAL PRIMARY KEY,
    tipo VARCHAR(50)
);

CREATE TABLE pagos (
    pago_id SERIAL PRIMARY KEY,
    pedido_id INT REFERENCES pedidos(pedido_id),
    medio_id INT REFERENCES medios_pago(medio_id),
    monto DECIMAL(10,2)
);

CREATE TABLE calificaciones (
    calificacion_id SERIAL PRIMARY KEY,
    pedido_id INT REFERENCES pedidos(pedido_id),
    puntuacion INT,
    comentario TEXT
);

-- Insertar datos de ejemplo
INSERT INTO clientes (nombre, direccion, email, telefono) VALUES
('Ana Torres', 'Av. Central 123', 'ana@example.com', '912345678'),
('Luis Pérez', 'Calle Norte 456', 'luis@example.com', '987654321');

INSERT INTO repartidores (nombre, telefono) VALUES
('Carlos Gómez', '911112223'),
('Sofía Rojas', '922223334');

INSERT INTO empresas (nombre, direccion, tipo_servicio) VALUES
('Farmacia Salud', 'Av. Salud 101', 'medicamentos'),
('Express Documentos', 'Calle Oficina 22', 'documentos');

INSERT INTO productos (empresa_id, nombre, descripcion, precio, requiere_receta, categoria) VALUES
(1, 'Paracetamol 500mg', 'Analgésico y antipirético', 2500, false, 'Medicamentos'),
(1, 'Amoxicilina 500mg', 'Antibiótico', 4200, true, 'Medicamentos'),
(2, 'Envío carta notarial', 'Servicio de entrega certificada', 8000, false, 'Documentos');

INSERT INTO pedidos (cliente_id, empresa_id, repartidor_id, fecha, estado) VALUES
(1, 1, 1, '2025-05-20 09:00:00', 'entregado'),
(2, 2, 2, '2025-05-21 14:00:00', 'cancelado');

INSERT INTO detalle_pedidos (pedido_id, producto_id, cantidad) VALUES
(1, 1, 2),
(1, 2, 1),
(2, 3, 1);

INSERT INTO medios_pago (tipo) VALUES ('Tarjeta de crédito'), ('Efectivo');

INSERT INTO pagos (pedido_id, medio_id, monto) VALUES
(1, 1, 9200),
(2, 2, 8000);

INSERT INTO calificaciones (pedido_id, puntuacion, comentario) VALUES
(1, 5, 'Muy buen servicio y rápido.');

-- Insertar datos adicionales
INSERT INTO clientes (nombre, direccion, email, telefono) VALUES
('María López', 'Calle Luna 123', 'maria@example.com', '911111111'),
('Pedro Ramírez', 'Av. Sol 456', 'pedro@example.com', '922222222'),
('Lucía Díaz', 'Jr. Estrella 789', 'lucia@example.com', '933333333');
INSERT INTO repartidores (nombre, telefono) VALUES
('Miguel Torres', '933445566'),
('Elena Mendoza', '944556677'),
('Raúl Vargas', '955667788');

INSERT INTO pedidos (cliente_id, empresa_id, repartidor_id, fecha, estado) VALUES
(3, 1, 3, '2025-03-22 10:30:00', 'entregado'),
(4, 2, 4, '2025-03-22 12:15:00', 'entregado'),
(5, 1, 5, '2025-03-23 09:45:00', 'entregado'),
(1, 2, 3, '2025-03-24 11:00:00', 'entregado'),
(2, 1, 3, '2025-03-25 13:00:00', 'entregado'),
(3, 2, 3, '2025-03-26 15:00:00', 'entregado'),
(4, 1, 5, '2025-03-27 09:30:00', 'entregado'),
(5, 2, 4, '2025-03-28 14:00:00', 'entregado'),
(1, 1, 2, '2025-03-29 16:00:00', 'entregado'),
(2, 2, 2, '2025-03-30 17:30:00', 'entregado');

INSERT INTO detalle_pedidos (pedido_id, producto_id, cantidad) VALUES
(3, 1, 1),
(3, 2, 1),
(4, 3, 1),
(5, 1, 2),
(6, 3, 1),
(7, 1, 1),
(8, 2, 1),
(9, 3, 2),
(10, 1, 1),
(10, 2, 1);

INSERT INTO pagos (pedido_id, medio_id, monto) VALUES
(3, 1, 6700),
(4, 2, 8000),
(5, 1, 5000),
(6, 2, 8000),
(7, 1, 2500),
(8, 1, 4200),
(9, 2, 16000),
(10, 2, 6700);

INSERT INTO calificaciones (pedido_id, puntuacion, comentario) VALUES
(3, 5, 'Muy amable y rápido.'),
(4, 4, 'Buen servicio.'),
(5, 3, 'Tardó un poco, pero correcto.'),
(6, 5, 'Excelente. Muy recomendado.'),
(7, 4, 'Bien.'),
(8, 5, 'Rápido y eficiente.'),
(9, 2, 'Se equivocó en la entrega.'),
(10, 5, 'Perfecto.');

-- Consultas SQL complejas
-- 1. ¿Qué cliente ha gastado más dinero en pedidos entregados?
SELECT sp.id_cliente, c.nombre AS cliente, sp.num_pedidos AS num_pedidos_pagados, sp.suma_pagos
FROM clientes c
INNER JOIN (SELECT p.cliente_id AS id_cliente, COUNT(*) AS num_pedidos, SUM(pa.monto) AS suma_pagos
			FROM pedidos p
			INNER JOIN pagos pa ON p.pedido_id = pa.pedido_id
			WHERE p.estado = 'entregado'
			GROUP BY p.cliente_id) AS sp
ON c.cliente_id = sp.id_cliente
WHERE sp.suma_pagos = (SELECT MAX(suma_pagos) AS max_pagos
					   FROM (SELECT p.cliente_id, SUM(pa.monto) AS suma_pagos
						     FROM pedidos p
						     INNER JOIN pagos pa ON p.pedido_id = pa.pedido_id
						     WHERE p.estado = 'entregado'
						     GROUP BY p.cliente_id));

-- 2. ¿Cuáles son los productos o servicios más pedidos en el último mes por categoría?
SELECT categoria, producto_id, nombre, total_cantidad
FROM (
    SELECT 
        p.categoria,
        p.producto_id,
        p.nombre,
        SUM(dp.cantidad) AS total_cantidad,
        RANK() OVER(PARTITION BY p.categoria ORDER BY SUM(dp.cantidad) DESC) AS rnk
    FROM pedidos pe
    INNER JOIN detalle_pedidos dp ON pe.pedido_id = dp.pedido_id
    INNER JOIN productos p ON dp.producto_id = p.producto_id
    WHERE pe.fecha >= CURRENT_DATE - INTERVAL '1 month'
    GROUP BY p.categoria, p.producto_id, p.nombre
) t
WHERE rnk = 1
ORDER BY categoria;

-- 3. Listar las empresas asociadas con más entregas fallidas.
SELECT e.empresa_id, e.nombre AS empresa_nombre, COUNT(p.pedido_id) AS pedidos_cancelados_count
FROM empresas e
LEFT JOIN pedidos p ON e.empresa_id = p.empresa_id
WHERE p.estado = 'cancelado'
GROUP BY e.empresa_id, e.nombre
HAVING COUNT(p.pedido_id) > 0
ORDER BY pedidos_cancelados_count DESC;

-- 4. Calcular el tiempo promedio entre pedido y entrega por repartidor.

-- 5. Obtener los 3 repartidores con mejor rendimiento (basado en entregas y puntuación).
SELECT r.nombre as repartidor, COUNT(p.pedido_id) as pedidos, AVG(c.puntuacion) as calificación_avg
FROM (repartidores as r
INNER JOIN pedidos as p ON r.repartidor_id = p.repartidor_id) 
FULL JOIN calificaciones as c ON p.pedido_id = c.pedido_id
WHERE p.estado = 'entregado'
GROUP BY r.repartidor_id
ORDER BY COUNT(p.pedido_id) DESC, AVG(c.puntuacion) DESC
LIMIT 3;
-- 6. ¿Qué medio de pago se utiliza más en pedidos urgentes?


-- Procedimientos almacenados
-- 7. Registrar un pedido completo.

-- 8. Cambiar el estado de un pedido con validación.

-- 9. Descontar stock al confirmar pedido (si aplica).


-- Triggers
-- 10. Insertar automáticamente la fecha de entrega al marcar como entregado.

-- 11. Registrar una notificación si se detecta un problema crítico en el pedido.
CREATE TABLE notificaciones (
    notificacion_id SERIAL PRIMARY KEY,
    pedido_id INT REFERENCES pedidos(pedido_id),
    mensaje VARCHAR(50),
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE OR REPLACE FUNCTION registrar_notificacion_critica()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.estado = 'critico' THEN
        INSERT INTO notificaciones (pedido_id, mensaje)
        VALUES (NEW.pedido_id, 'Pedido critico.');
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER notificacion_pedido_critico
AFTER UPDATE OF estado ON pedidos
FOR EACH ROW
WHEN (NEW.estado = 'critico')
EXECUTE FUNCTION registrar_notificacion_critica();


-- 12. Insertar una calificación automática si no se recibe en 48 horas.
CREATE OR REPLACE FUNCTION calificacion_auto() RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO calificaciones ("pedido_id", "puntuacion", "comentario")
    SELECT p.pedido_id, 3, 'calificado automaticamente'
    FROM pedidos p
    LEFT JOIN calificaciones c ON p.pedido_id = c.pedido_id
    WHERE c.pedido_id IS NULL
    AND p.fecha + INTERVAL '48 hours' <= NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER activar_calificacion_auto
	AFTER INSERT ON pedidos
	FOR EACH STATEMENT
	EXECUTE FUNCTION calificacion_auto();


-- Vistas
-- 13. Resumen de pedidos por cliente (monto total, número de pedidos).
CREATE OR REPLACE VIEW resumen_pedidos_x_cliente AS
	SELECT p.cliente_id id, c.nombre AS nombre_cliente, COUNT(*) AS num_pedidos, SUM(pa.monto) AS monto_total
	FROM pedidos p
	INNER JOIN pagos pa ON p.pedido_id = pa.pedido_id
	INNER JOIN clientes c ON c.cliente_id = p.cliente_id
	WHERE p.estado = 'entregado'
	GROUP BY p.cliente_id, c.nombre;

-- 14. Vista de desempeño por repartidor.

-- 15. Vista de empresas asociadas con mayor volumen de paquetes entregados.
CREATE VIEW empresas_mas_pedidos_entregados AS
SELECT e.empresa_id, e.nombre AS empresa_nombre, COUNT(p.pedido_id) AS total_pedidos_entregados
FROM empresas e
LEFT JOIN pedidos p ON e.empresa_id = p.empresa_id
WHERE p.estado = 'entregado'
GROUP BY e.empresa_id, e.nombre
ORDER BY total_pedidos_entregados DESC;