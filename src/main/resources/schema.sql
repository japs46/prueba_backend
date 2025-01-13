-- Crear tabla de franquicias si no existe
CREATE TABLE IF NOT EXISTS Franquicias (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

-- Crear tabla de sucursales si no existe
CREATE TABLE IF NOT EXISTS Sucursales (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    id_franquicia BIGINT NOT NULL,
    FOREIGN KEY (id_franquicia) REFERENCES Franquicias(id) ON DELETE CASCADE
);

-- Crear tabla de productos si no existe
CREATE TABLE IF NOT EXISTS Productos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    stock INT NOT NULL,
    id_sucursal BIGINT NOT NULL,
    FOREIGN KEY (id_sucursal) REFERENCES Sucursales(id) ON DELETE CASCADE
);
