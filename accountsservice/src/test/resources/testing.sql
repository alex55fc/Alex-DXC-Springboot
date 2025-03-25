-- Insertar datos en la tabla customers
INSERT INTO CUSTOMER (id, name, email) VALUES
(6, 'Juan Pérez', 'juan.perez@example.com'),
(7, 'María Gómez', 'maria.gomez@example.com'),
(8, 'Carlos López', 'carlos.lopez@example.com'),
(9, 'Ana Martínez', 'ana.martinez@example.com'),
(10, 'Luis Rodríguez', 'luis.rodriguez@example.com');

-- Insertar datos en la tabla account
INSERT INTO ACCOUNT(type, opening_date,balance, owner_id) VALUES
('Ahorro', '2023-01-15', 1500, 6),
('Corriente', '2023-03-22', 2500, 7),
('Ahorro', '2024-06-10', 3000, 8),
('Corriente', '2022-11-30', 500, 9),
('Ahorro', '2025-01-05', 1200, 10);