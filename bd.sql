-- Creación de secuencias para las llaves primarias
CREATE SEQUENCE seq_TipoUsuario START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_Usuario START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_Categoria START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_Productos START WITH 1 INCREMENT BY 1;

-- Tabla TipoUsuario
CREATE TABLE TipoUsuario (
    idTipoUsuario NUMBER PRIMARY KEY,
    nombreTipo VARCHAR2(50) NOT NULL,
    descripcion VARCHAR2(100)
);

-- Tabla Usuario
CREATE TABLE Usuario (
    idUsuario NUMBER PRIMARY KEY,
    nombre VARCHAR2(50) NOT NULL,
    apellido VARCHAR2(50) NOT NULL,
    email VARCHAR2(100) UNIQUE NOT NULL,
    contrasena VARCHAR2(255) NOT NULL,
    fechaRegistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    idTipoUsuario NUMBER NOT NULL,
    CONSTRAINT fk_Usuario_TipoUsuario FOREIGN KEY (idTipoUsuario) REFERENCES TipoUsuario(idTipoUsuario)
);

-- Tabla Categoria
CREATE TABLE Categoria (
    idCategoria NUMBER PRIMARY KEY,
    nombreCategoria VARCHAR2(50) NOT NULL,
    descripcion VARCHAR2(200),
    fechaCreacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla Productos
CREATE TABLE Productos (
    idProducto NUMBER PRIMARY KEY,
    nombreProducto VARCHAR2(100) NOT NULL,
    descripcion CLOB,
    precio NUMBER(10,2) NOT NULL,
    stock NUMBER DEFAULT 0,
    fechaIngreso TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    idCategoria NUMBER NOT NULL,
    idUsuarioRegistro NUMBER NOT NULL,
    CONSTRAINT fk_Productos_Categoria FOREIGN KEY (idCategoria) REFERENCES Categoria(idCategoria),
    CONSTRAINT fk_Productos_Usuario FOREIGN KEY (idUsuarioRegistro) REFERENCES Usuario(idUsuario)
);

ALTER TABLE PRODUCTOS 
ADD (imagen_url VARCHAR2(120));

-- Triggers para autoincrementar las llaves primarias usando las secuencias
CREATE OR REPLACE TRIGGER trg_TipoUsuario
BEFORE INSERT ON TipoUsuario
FOR EACH ROW
BEGIN
    SELECT seq_TipoUsuario.NEXTVAL INTO :new.idTipoUsuario FROM dual;
END;
/

CREATE OR REPLACE TRIGGER trg_Usuario
BEFORE INSERT ON Usuario
FOR EACH ROW
BEGIN
    SELECT seq_Usuario.NEXTVAL INTO :new.idUsuario FROM dual;
END;
/

CREATE OR REPLACE TRIGGER trg_Categoria
BEFORE INSERT ON Categoria
FOR EACH ROW
BEGIN
    SELECT seq_Categoria.NEXTVAL INTO :new.idCategoria FROM dual;
END;
/

CREATE OR REPLACE TRIGGER trg_Productos
BEFORE INSERT ON Productos
FOR EACH ROW
BEGIN
    SELECT seq_Productos.NEXTVAL INTO :new.idProducto FROM dual;
END;
/

-- Insertar datos iniciales en TipoUsuario
INSERT INTO TipoUsuario (nombreTipo, descripcion) VALUES 
('Administrador', 'Acceso completo al sistema');
INSERT INTO TipoUsuario (nombreTipo, descripcion) VALUES 
('Almacenista', 'Gestiona inventario y productos');
INSERT INTO TipoUsuario (nombreTipo, descripcion) VALUES 
('Cliente', 'Usuario que realiza compras');

-- Insertar datos iniciales en Usuario
INSERT INTO Usuario (nombre, apellido, email, contrasena, idTipoUsuario) VALUES 
('Juan', 'Pérez', 'admin@techstore.com', 'hashed_password_123', 1);
INSERT INTO Usuario (nombre, apellido, email, contrasena, idTipoUsuario) VALUES 
('María', 'Gómez', 'maria@techstore.com', 'hashed_password_456', 2);
INSERT INTO Usuario (nombre, apellido, email, contrasena, idTipoUsuario) VALUES 
('Carlos', 'López', 'cliente1@email.com', 'hashed_password_789', 3);

-- Insertar datos iniciales en Categoria
INSERT INTO Categoria (nombreCategoria, descripcion) VALUES 
('Computadoras', 'Laptops, PCs de escritorio y todo en uno');
INSERT INTO Categoria (nombreCategoria, descripcion) VALUES 
('Componentes', 'Procesadores, tarjetas gráficas, memorias RAM');
INSERT INTO Categoria (nombreCategoria, descripcion) VALUES 
('Periféricos', 'Teclados, mouse, monitores, audífonos');
INSERT INTO Categoria (nombreCategoria, descripcion) VALUES 
('Almacenamiento', 'Discos duros, SSDs, memorias USB');
INSERT INTO Categoria (nombreCategoria, descripcion) VALUES 
('Redes', 'Routers, switches, tarjetas de red');

-- Insertar datos iniciales en Productos
INSERT INTO Productos (nombreProducto, descripcion, precio, stock, idCategoria, idUsuarioRegistro) VALUES 
('Laptop HP Pavilion', 'Laptop 15.6", Intel Core i5, 8GB RAM, 512GB SSD', 899.99, 15, 1, 2);
INSERT INTO Productos (nombreProducto, descripcion, precio, stock, idCategoria, idUsuarioRegistro) VALUES 
('Teclado Mecánico Redragon', 'Teclado mecánico K552 con switches Outemu Blue', 59.99, 30, 3, 2);
INSERT INTO Productos (nombreProducto, descripcion, precio, stock, idCategoria, idUsuarioRegistro) VALUES 
('SSD Samsung 1TB', 'Disco sólido Samsung 970 EVO Plus NVMe M.2', 129.99, 25, 4, 2);
INSERT INTO Productos (nombreProducto, descripcion, precio, stock, idCategoria, idUsuarioRegistro) VALUES 
('Router TP-Link Archer', 'Router WiFi 6 AX1500 dual band', 79.99, 18, 5, 2);
INSERT INTO Productos (nombreProducto, descripcion, precio, stock, idCategoria, idUsuarioRegistro) VALUES 
('Monitor LG 24"', 'Monitor Full HD 24" IPS, 75Hz', 149.99, 12, 3, 2);

COMMIT;
