-- Crear tablas principales para el sistema de delivery
CREATE TABLE IF NOT EXISTS clientes (
    cliente_id SERIAL PRIMARY KEY,
    nombre VARCHAR(100),
    direccion TEXT,
    email VARCHAR(100),
    telefono VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS repartidores (
    repartidor_id SERIAL PRIMARY KEY,
    nombre VARCHAR(100),
    telefono VARCHAR(20),
    disponible BOOLEAN DEFAULT true
);

CREATE TABLE IF NOT EXISTS empresas (
    empresa_id SERIAL PRIMARY KEY,
    nombre VARCHAR(100),
    direccion TEXT,
    tipo_servicio VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS productos (
    producto_id SERIAL PRIMARY KEY,
    empresa_id INT REFERENCES empresas(empresa_id),
    nombre VARCHAR(100),
    descripcion TEXT,
    precio DECIMAL(10,2),
    requiere_receta BOOLEAN DEFAULT false,
    categoria VARCHAR(100),
    stock INT DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS pedidos (
    pedido_id SERIAL PRIMARY KEY,
    cliente_id INT REFERENCES clientes(cliente_id),
    empresa_id INT REFERENCES empresas(empresa_id),
    repartidor_id INT REFERENCES repartidores(repartidor_id),
    fecha TIMESTAMP,
    fecha_entrega timestamp,
    estado VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS detalle_pedidos (
    detalle_id SERIAL PRIMARY KEY,
    pedido_id INT REFERENCES pedidos(pedido_id),
    producto_id INT REFERENCES productos(producto_id),
    cantidad INT
);

CREATE TABLE IF NOT EXISTS medios_pago (
    medio_id SERIAL PRIMARY KEY,
    tipo VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS pagos (
    pago_id SERIAL PRIMARY KEY,
    pedido_id INT REFERENCES pedidos(pedido_id),
    medio_id INT REFERENCES medios_pago(medio_id),
    monto DECIMAL(10,2)
);

CREATE TABLE IF NOT EXISTS calificaciones (
    calificacion_id SERIAL PRIMARY KEY,
    pedido_id INT REFERENCES pedidos(pedido_id),
    puntuacion INT,
    comentario TEXT
);

CREATE TABLE IF NOT EXISTS urgencias (
    urgencia_id SERIAL PRIMARY KEY,
    pedido_id INT UNIQUE REFERENCES pedidos(pedido_id),
    nivel VARCHAR(10) NOT NULL CHECK (nivel IN ('urgente','no urgente'))
);

CREATE TABLE IF NOT EXISTS usuario (
    idUsuario SERIAL PRIMARY KEY,
    rut VARCHAR(20) NOT NULL,
    nameParam VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    birthdate DATE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL
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

INSERT INTO productos (empresa_id, nombre, descripcion, precio, requiere_receta, categoria, stock) VALUES
(1, 'Paracetamol 500mg', 'Analgésico y antipirético', 2500, false, 'Medicamentos', 100),
(1, 'Amoxicilina 500mg', 'Antibiótico', 4200, true, 'Medicamentos', 200),
(2, 'Envío carta notarial', 'Servicio de entrega certificada', 8000, false, 'Documentos', NULL);

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

INSERT INTO urgencias (pedido_id, nivel)
VALUES 
    (1, 'urgente'),
    (2, 'no urgente'),
    (3, 'urgente'),
    (4, 'no urgente'),
    (5, 'urgente');

INSERT INTO usuario (rut, nameParam, email, phone, birthdate, password, role)
VALUES 
('11111111-1', 'Usuario Administrador', 'admin@example.com', '123456789', '1980-01-01', 'adminPass', 'ADMIN'),
('22222222-2', 'Usuario Trabajador', 'trabajador@example.com', '987654321', '1990-05-15', 'trabajadorPass', 'TRABAJADOR'),
('33333333-3', 'Usuario Cliente', 'cliente@example.com', '555555555', '2000-09-09', 'clientePass', 'CLIENTE');

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
)
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
SELECT r.repartidor_id, r.nombre, r.telefono, r.disponible, AVG(EXTRACT(EPOCH FROM p.fecha_entrega - p.fecha) / 86400) as avg_tiempo_entrega_min
FROM repartidores as r
INNER JOIN pedidos as p ON r.repartidor_id = p.repartidor_id
WHERE p.estado = 'entregado'
GROUP BY r.repartidor_id;


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
SELECT mp.tipo AS medio_pago, COUNT(*) AS total_urgentes
FROM pagos pg
JOIN urgencias u ON pg.pedido_id = u.pedido_id
JOIN medios_pago mp ON pg.medio_id = mp.medio_id
WHERE u.nivel = 'urgente'
GROUP BY mp.tipo
ORDER BY COUNT(*) DESC
LIMIT 1;


-- 7. Registrar un pedido completo.  --se pone el OR REPLACE en caso de que ya exista ese pedido
CREATE OR REPLACE FUNCTION registrar_pedido(
       p_cliente_id INT,
       p_empresa_id INT,
       p_repartidor_id INT,
       p_estado VARCHAR,
       p_productos INT[], --lista de id de productos
       p_cantidades INT[],
       p_medio_pago_id INT
)
RETURNS INT AS $$
DECLARE
       nuevo_id INT; --id del pedido
       i INT; -- indice, empieza en 1
BEGIN
    -- primeros insertamos el pedido
    INSERT INTO pedidos (cliente_id, empresa_id, repartidor_id, fecha, estado)
    VALUES (p_cliente_id, p_empresa_id, p_repartidor_id, NOW()::timestamp(0), p_estado)
    RETURNING pedido_id INTO nuevo_id;

    -- ahora insertamos el detalle del pedido
    FOR I IN 1..array_length(p_productos, 1) LOOP --recorremos desde 1 hasta el fin del arreglo de los productos
        INSERT INTO detalle_pedidos (pedido_id, producto_id, cantidad) -- insertamos producto por producto en detalle_pedidos
        VALUES (nuevo_id, p_productos[i], p_cantidades[i]);
    END LOOP;

    -- luego, insertamos en pagos con su precio
    INSERT INTO pagos (pedido_id, medio_id, monto)
    SELECT nuevo_id, p_medio_pago_id,
           SUM(p.precio * DP.cantidad)
    FROM detalle_pedidos DP
    JOIN productos P ON P.producto_id = DP.producto_id
    WHERE DP.pedido_id = nuevo_id
    GROUP BY DP.pedido_id; --agrupamos x id del pedido

    RETURN nuevo_id;
END;
$$ LANGUAGE plpgsql;
--ejemplo:
SELECT registrar_pedido(
               1,              -- cliente_id
               1,              -- empresa_id
               2,              -- repartidor_id
               'en camino',    -- estado del pedido
               ARRAY[1, 2],    -- productos
               ARRAY[2, 1],    -- cantidades
               2               -- medio de pago (efectivo)
);

-- 8. Cambiar el estado de un pedido con validación. La validación que puse es que si ya fue entregado o cancelado no se puede cambiar (el cambio seria para poner que ya fue entregado)
CREATE OR REPLACE FUNCTION cambiar_estado_pedido(
    p_pedido_id INT,
    p_nuevo_estado VARCHAR)
RETURNS VOID AS $$
BEGIN
    -- verficamos que el pedido exista
    IF NOT EXISTS (
            SELECT 1 FROM pedidos
            WHERE pedido_id = p_pedido_id
    ) THEN
        RAISE EXCEPTION 'En la función cambiar_estado_pedido. Se ingresó un pedido de id % que no existe.', p_pedido_id;
    END IF;

    --verificamos q el estado sea valido para cambiarlo
    IF EXISTS (
        SELECT 1 FROM pedidos
        WHERE pedido_id = p_pedido_id
          AND estado IN ('entregado', 'cancelado') --validación, no puede tener estos estados
    ) THEN
        RAISE EXCEPTION 'En la función cambiar_estado_pedido. Se intentó cambiar un estado del pedido %, que ya fue entregado o cancelado.', p_pedido_id;
    end if;

    --validamos q el nuevo estado no este vacio
    IF p_nuevo_estado IS NULL OR TRIM(p_nuevo_estado) = '' THEN --si esta vacio o tiene espacios
        RAISE EXCEPTION 'En la función cambiar_estado_pedido. Se ingresó un estado vacío, por favor rellenarlo';
    END IF;

    UPDATE pedidos
    SET estado = p_nuevo_estado
    WHERE pedido_id = p_pedido_id;
end;
$$ LANGUAGE plpgsql;
--ejemplos:
--SELECT cambiar_estado_pedido(666, 'en camino'); --lanza la excepción q no existe el pedido
--SELECT cambiar_estado_pedido(2, 'en camino'); -- lanza excepción q ya fue cancelado
--SELECT cambiar_estado_pedido(74, 'entregado');-- se cambia del estado 'en camino' a 'entregado'

-- 9. Descontar stock al confirmar pedido (si aplica).
CREATE OR REPLACE FUNCTION confirmar_pedido_descontar(p_pedido_id INT)
RETURNS VOID AS $$
BEGIN
    -- Verificar que el pedido exista
    IF NOT EXISTS (
        SELECT 1 FROM pedidos
        WHERE pedido_id = p_pedido_id
    ) THEN
        RAISE EXCEPTION 'No existe el pedido con ID %', p_pedido_id;
    END IF;

    -- Cambiar estado a 'entregado'
    UPDATE pedidos
    SET estado = 'entregado'
    WHERE pedido_id = p_pedido_id;

    -- Descontar stock solo en productos que tengan stock definido
    UPDATE productos
    SET stock = stock - dp.cantidad
    FROM detalle_pedidos dp
    WHERE productos.producto_id = dp.producto_id
      AND dp.pedido_id = p_pedido_id
      AND productos.stock IS NOT NULL;
END;
$$ LANGUAGE plpgsql;
-- Ejmplo de uso: 
-- SELECT confirmar_pedido_descontar(1);
-- En este caso, se cambiara a entregado (el pedido 1) y se descontará el stock de los productos en el pedido con ID 1.

-- Triggers
-- 10. Insertar automáticamente la fecha de entrega al marcar como entregado.
--función del trigger
CREATE OR REPLACE FUNCTION insertar_fecha_entrega()
RETURNS TRIGGER AS $$
BEGIN
    --primero hay q verificar q este entregado
    IF NEW.estado = 'entregado'
        THEN
        NEW.fecha_entrega = NOW()::timestamp(0);
    end if;
    RETURN NEW;
end;
$$ LANGUAGE plpgsql;

--trigger
CREATE TRIGGER trigger_insertar_fecha_entrega
BEFORE UPDATE ON pedidos
FOR EACH ROW
WHEN (OLD.estado IS DISTINCT FROM
    'entregado' AND NEW.estado = 'entregado')
EXECUTE FUNCTION insertar_fecha_entrega();


-- 11. Registrar una notificación si se detecta un problema crítico en el pedido.
CREATE TABLE IF NOT EXISTS notificaciones (
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

CREATE OR REPLACE TRIGGER notificacion_pedido_critico
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
SELECT r.nombre as repartidor, COUNT(p.pedido_id) as pedidos_entregados, AVG(c.puntuacion) as calificación_avg
FROM (repartidores as r
    INNER JOIN pedidos as p ON r.repartidor_id = p.repartidor_id)
         FULL JOIN calificaciones as c ON p.pedido_id = c.pedido_id
WHERE p.estado = 'entregado'
GROUP BY r.repartidor_id;
-- 15. Vista de empresas asociadas con mayor volumen de paquetes entregados.
CREATE OR REPLACE VIEW empresas_mas_pedidos_entregados AS
SELECT e.empresa_id, e.nombre AS empresa_nombre, COUNT(p.pedido_id) AS total_pedidos_entregados
FROM empresas e
LEFT JOIN pedidos p ON e.empresa_id = p.empresa_id
WHERE p.estado = 'entregado'
GROUP BY e.empresa_id, e.nombre
ORDER BY total_pedidos_entregados DESC;