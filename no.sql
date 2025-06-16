-- Activar extensión PostGIS
CREATE EXTENSION IF NOT EXISTS postgis;
-- Activar pgRouting
CREATE EXTENSION IF NOT EXISTS pgrouting;

-- Crear tablas principales para el sistema de delivery
CREATE TABLE IF NOT EXISTS clientes (
    cliente_id SERIAL PRIMARY KEY,
    nombre VARCHAR(100),
    direccion TEXT,
    email VARCHAR(100),
    telefono VARCHAR(20),
    ubicacion GEOMETRY(Point, 4326)
);

CREATE TABLE IF NOT EXISTS repartidores (
    repartidor_id SERIAL PRIMARY KEY,
    nombre VARCHAR(100),
    telefono VARCHAR(20),
    disponible BOOLEAN DEFAULT true,
    ubicacion_actual GEOMETRY(Point, 4326)
);

CREATE TABLE IF NOT EXISTS empresas (
    empresa_id SERIAL PRIMARY KEY,
    nombre VARCHAR(100),
    direccion TEXT,
    tipo_servicio VARCHAR(50),
    ubicacion GEOMETRY(Point, 4326)
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
    fecha TIMESTAMP(0),
    fecha_entrega TIMESTAMP(0),
    estado VARCHAR(50),
    ruta_estimada GEOMETRY(LineString, 4326),
    punto_inicio GEOMETRY(Point, 4326),
    punto_final GEOMETRY(Point, 4326)
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

-- Crear tabla de zonas de cobertura
CREATE TABLE zonas_cobertura (
    zona_id SERIAL PRIMARY KEY,
    nombre VARCHAR(100),
    geom GEOMETRY(Polygon, 4326)
);






--Seccion extra para calculo ruta_estimada
CREATE TABLE IF NOT EXISTS calles (
    street_id SERIAL PRIMARY KEY,
    fid INT,
    shape_leng NUMERIC,
    st_length_ NUMERIC,
    nom_ruta VARCHAR(255),
    comuna VARCHAR(100),
    geom GEOMETRY(MultiLineString, 4326) -- SRID 4326 for WGS84
);

--Nueva tabla para filtrar las calles por secciones unicas, con nuevo Id
--Esto se necesita por la base de datos que utilizamos
CREATE TABLE calles_cleaned (
    cleaned_street_id SERIAL PRIMARY KEY, -- New unique ID for each LineString segment
    street_id INT, -- Keep the original ID for reference
    fid INT,
    shape_leng NUMERIC,
    st_length_ NUMERIC,
    nom_ruta VARCHAR(255),
    comuna VARCHAR(100),
    geom GEOMETRY(LineString, 4326),
    source INTEGER,                        
    target INTEGER,                        
    cost DOUBLE PRECISION
);


--Importar archivos csv de calles_cleaned y calles_cleaned_vertices_pgr



--Funcion para snapear nodos a la topologia
CREATE OR REPLACE FUNCTION find_nearest_node(input_point GEOMETRY(Point, 4326))
RETURNS BIGINT AS $$
DECLARE
    nearest_node_id BIGINT;
BEGIN
    SELECT
        id
    INTO
        nearest_node_id
    FROM
        calles_cleaned_vertices_pgr
    ORDER BY
        ST_Distance(the_geom, input_point)
    LIMIT 1;

    -- Opcional: Añadir error handling si no se encuentra un nodo cercano (por ejemplo, si el nodo inicial esta muy lejos de la red/topologia)
    IF nearest_node_id IS NULL THEN
        RAISE EXCEPTION 'No network node found near the input point.';
    END IF;

    RETURN nearest_node_id;
END;
$$ LANGUAGE plpgsql;

-- Clientes
INSERT INTO clientes (nombre, direccion, email, telefono, ubicacion) VALUES
('Ana Rojas', 'Av. Ecuador 1234', 'ana.rojas@example.com', '+56911111111', ST_SetSRID(ST_MakePoint(-70.681, -33.456), 4326)),
('Carlos Díaz', 'Matucana 456', 'carlos.diaz@example.com', '+56922222222', ST_SetSRID(ST_MakePoint(-70.683, -33.457), 4326)),
('María López', 'Av. Libertador 789', 'maria.lopez@example.com', '+56933333333', ST_SetSRID(ST_MakePoint(-70.685, -33.455), 4326)),
('Pedro González', 'San Alfonso 321', 'pedro.g@example.com', '+56944444444', ST_SetSRID(ST_MakePoint(-70.682, -33.458), 4326)),
('Lucía Herrera', 'Av. Las Rejas 654', 'lucia.h@example.com', '+56955555555', ST_SetSRID(ST_MakePoint(-70.680, -33.459), 4326));

-- Cliente 1 → Zona Norte
UPDATE clientes
SET ubicacion = ST_SetSRID(ST_MakePoint(-70.686, -33.453), 4326)
WHERE cliente_id = 1;
-- Cliente 3 → Zona Sur
UPDATE clientes
SET ubicacion = ST_SetSRID(ST_MakePoint(-70.686, -33.461), 4326)
WHERE cliente_id = 3;

-- Cliente 4 → Zona Oeste
UPDATE clientes
SET ubicacion = ST_SetSRID(ST_MakePoint(-70.690, -33.455), 4326)
WHERE cliente_id = 4;

-- Cliente 5 → Zona Este
UPDATE clientes
SET ubicacion = ST_SetSRID(ST_MakePoint(-70.678, -33.455), 4326)
WHERE cliente_id = 5;


-- Repartidores
INSERT INTO repartidores (nombre, telefono, disponible, ubicacion_actual) VALUES
('Jorge Silva', '+56966666666', true, ST_SetSRID(ST_MakePoint(-70.681, -33.456), 4326)),
('Camila Torres', '+56977777777', true, ST_SetSRID(ST_MakePoint(-70.682, -33.457), 4326)),
('Luis Ramírez', '+56988888888', false, ST_SetSRID(ST_MakePoint(-70.683, -33.458), 4326)),
('Valentina Pino', '+56999999999', true, ST_SetSRID(ST_MakePoint(-70.684, -33.459), 4326)),
('Diego Fuentes', '+56900000000', true, ST_SetSRID(ST_MakePoint(-70.685, -33.460), 4326));

-- Empresas
INSERT INTO empresas (nombre, direccion, tipo_servicio, ubicacion) VALUES
('Don Burger', 'Matucana 717', 'Comida rápida', ST_SetSRID(ST_MakePoint(-70.683, -33.456), 4326)),
('TodoPizza', 'Av. Ecuador 4901', 'Pizzería', ST_SetSRID(ST_MakePoint(-70.684, -33.457), 4326)),
('Farmacia Central', 'Av. Libertador 1750', 'Farmacia', ST_SetSRID(ST_MakePoint(-70.685, -33.458), 4326)),
('Empanadas Express', 'San Alfonso 100', 'Comida chilena', ST_SetSRID(ST_MakePoint(-70.686, -33.459), 4326)),
('Sushi Go', 'Av. Las Rejas 200', 'Sushi', ST_SetSRID(ST_MakePoint(-70.687, -33.460), 4326));

-- Medios de pago
INSERT INTO medios_pago (tipo) VALUES
('Efectivo'), ('Tarjeta de crédito'), ('Tarjeta de débito'), ('Transferencia'), ('App de pago');

-- Usuarios
INSERT INTO usuario (rut, nameParam, email, phone, birthdate, password, role) VALUES
('12.345.678-9', 'admin', 'admin@delivery.cl', '+56912345678', '1990-01-01', 'admin123', 'ADMIN'),
('11.111.111-1', 'cliente1', 'cliente1@delivery.cl', '+56911112222', '1995-05-05', 'pass1', 'CLIENTE'),
('22.222.222-2', 'cliente2', 'cliente2@delivery.cl', '+56922223333', '1992-02-02', 'pass2', 'CLIENTE'),
('33.333.333-3', 'repartidor1', 'repartidor1@delivery.cl', '+56933334444', '1993-03-03', 'pass3', 'REPARTIDOR'),
('44.444.444-4', 'empresa1', 'empresa1@delivery.cl', '+56944445555', '1988-08-08', 'pass4', 'EMPRESA');

-- Productos
INSERT INTO productos (empresa_id, nombre, descripcion, precio, requiere_receta, categoria, stock) VALUES
(1, 'Hamburguesa Clásica', 'Pan, carne, lechuga, tomate y mayonesa', 4500, false, 'Comida rápida', 50),
(2, 'Pizza Napolitana', 'Queso, tomate, orégano y jamón', 7990, false, 'Pizzería', 30),
(3, 'Paracetamol 500mg', 'Caja de 20 comprimidos', 2500, true, 'Medicamento', 100),
(4, 'Empanada de Pino', 'Empanada tradicional chilena', 1800, false, 'Comida chilena', 60),
(5, 'Sushi Roll Salmón', '8 piezas con salmón y palta', 6900, false, 'Sushi', 40);

-- Pedidos
INSERT INTO pedidos (cliente_id, empresa_id, repartidor_id, fecha, fecha_entrega, estado, punto_inicio, punto_final, ruta_estimada) VALUES
(1, 1, 1, NOW(), NOW() + INTERVAL '30 minutes', 'Entregado',
 ST_SetSRID(ST_MakePoint(-70.681, -33.456), 4326),
 ST_SetSRID(ST_MakePoint(-70.683, -33.456), 4326),
 ST_SetSRID(ST_MakeLine(ST_MakePoint(-70.681, -33.456), ST_MakePoint(-70.683, -33.456)), 4326)),
(2, 2, 2, NOW(), NOW() + INTERVAL '40 minutes', 'En camino',
 ST_SetSRID(ST_MakePoint(-70.682, -33.457), 4326),
 ST_SetSRID(ST_MakePoint(-70.684, -33.457), 4326),
 ST_SetSRID(ST_MakeLine(ST_MakePoint(-70.682, -33.457), ST_MakePoint(-70.684, -33.457)), 4326)),
(3, 3, 3, NOW(), NOW() + INTERVAL '25 minutes', 'Entregado',
 ST_SetSRID(ST_MakePoint(-70.683, -33.458), 4326),
 ST_SetSRID(ST_MakePoint(-70.685, -33.458), 4326),
 ST_SetSRID(ST_MakeLine(ST_MakePoint(-70.683, -33.458), ST_MakePoint(-70.685, -33.458)), 4326)),
(4, 4, 4, NOW(), NOW() + INTERVAL '35 minutes', 'Pendiente',
 ST_SetSRID(ST_MakePoint(-70.684, -33.459), 4326),
 ST_SetSRID(ST_MakePoint(-70.686, -33.459), 4326),
 ST_SetSRID(ST_MakeLine(ST_MakePoint(-70.684, -33.459), ST_MakePoint(-70.686, -33.459)), 4326)),
(5, 5, 5, NOW(), NOW() + INTERVAL '20 minutes', 'Entregado',
 ST_SetSRID(ST_MakePoint(-70.685, -33.460), 4326),
 ST_SetSRID(ST_MakePoint(-70.687, -33.460), 4326),
 ST_SetSRID(ST_MakeLine(ST_MakePoint(-70.685, -33.460), ST_MakePoint(-70.687, -33.460)), 4326));

-- Detalle de pedidos
INSERT INTO detalle_pedidos (pedido_id, producto_id, cantidad) VALUES
(1, 1, 2),
(2, 2, 1),
(3, 3, 1),
(4, 4, 3),
(5, 5, 2);

-- Pagos
INSERT INTO pagos (pedido_id, medio_id, monto) VALUES
(1, 1, 9000),
(2, 2, 7990),
(3, 3, 2500),
(4, 3, 5400),
(5, 4, 13800);

-- Calificaciones
INSERT INTO calificaciones (pedido_id, puntuacion, comentario) VALUES
(1, 5, 'Muy rápido y sabroso'),
(2, 4, 'Buena pizza, pero llegó tibia'),
(3, 5, 'Excelente servicio'),
(4, 3, 'Demoró un poco'),
(5, 5, '¡Sushi fresco y delicioso!');

-- Urgencias
INSERT INTO urgencias (pedido_id, nivel) VALUES
(1, 'no urgente'),
(2, 'urgente'),
(3, 'urgente'),
(4, 'no urgente'),
(5, 'no urgente');

-- Zonas de cobertura
INSERT INTO zonas_cobertura (nombre, geom, empresa_id) VALUES
('Zona Norte', ST_GeomFromText('POLYGON((-70.688 -33.454, -70.688 -33.452, -70.684 -33.452, -70.684 -33.454, -70.688 -33.454))', 4326)),
('Zona Sur', ST_GeomFromText('POLYGON((-70.688 -33.460, -70.688 -33.462, -70.684 -33.462, -70.684 -33.460, -70.688 -33.460))', 4326)),
('Zona Este', ST_GeomFromText('POLYGON((-70.680 -33.456, -70.680 -33.454, -70.676 -33.454, -70.676 -33.456, -70.680 -33.456))', 4326)),
('Zona Oeste', ST_GeomFromText('POLYGON((-70.692 -33.456, -70.692 -33.454, -70.688 -33.454, -70.688 -33.456, -70.692 -33.456))', 4326)),
('Zona Centro', ST_GeomFromText('POLYGON((-70.686 -33.458, -70.686 -33.456, -70.682 -33.456, -70.682 -33.458, -70.686 -33.458))', 4326));

-- Calles
-- Si se importo archivo, no usar este (aunque, como calles esta vacio, calles_cleaned quedara vacio)
INSERT INTO calles_cleaned (street_id, fid, shape_leng, st_length_, nom_ruta, comuna, geom)
SELECT
    c.ogc_fid,
    c.fid,
    c.shape_leng,
    c.st_length_,
    c.nom_ruta,
    c.comuna,
    ST_GeometryN((ST_Dump(ST_LineMerge(c.geom))).geom, 1) --Hace un LineMerge al MultiLine de calles, Realiza un Dump a distintas filas en una tabla, .geom accede a la parte de geom de esa tabla, y GeometryN asegura que la operacion siempre devuelva LineString
FROM
    calles AS c;
