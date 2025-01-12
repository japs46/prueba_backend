-- Crear tabla de franquicias si no existe
CREATE TABLE IF NOT EXISTS Franquicias (
    id_franquicia BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

-- Crear tabla de sucursales si no existe
CREATE TABLE IF NOT EXISTS Sucursales (
    id_sucursal BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    id_franquicia BIGINT NOT NULL,
    FOREIGN KEY (id_franquicia) REFERENCES Franquicias(id_franquicia) ON DELETE CASCADE
);

-- Crear tabla de productos si no existe
CREATE TABLE IF NOT EXISTS Productos (
    id_producto BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    stock INT NOT NULL,
    id_sucursal BIGINT NOT NULL,
    FOREIGN KEY (id_sucursal) REFERENCES Sucursales(id_sucursal) ON DELETE CASCADE
);
