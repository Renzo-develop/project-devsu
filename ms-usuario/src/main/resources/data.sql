INSERT INTO CLIENTE (DIRECCION, EDAD, GENERO, IDENTIFICACION, NOMBRE, TELEFONO, CONTRASEÑA, ESTADO)
SELECT 'Urb Alisos, Comas', 25, 1, 41520221, 'Nataly', 901221544, 'secret', 1
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1
    FROM CLIENTE C
    WHERE C.IDENTIFICACION  = 41520221);

INSERT INTO CLIENTE (DIRECCION, EDAD, GENERO, IDENTIFICACION, NOMBRE, TELEFONO, CONTRASEÑA, ESTADO)
SELECT 'SMP', 31, 0, 41510222, 'Renzo', 903695545, 'secret', 1
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1
    FROM CLIENTE C
    WHERE C.IDENTIFICACION  = 41510222);

INSERT INTO CLIENTE (DIRECCION, EDAD, GENERO, IDENTIFICACION, NOMBRE, TELEFONO, CONTRASEÑA, ESTADO)
SELECT 'Miami', 21, 1, 45632923, 'Hyperx', 903692245, 'secret', 1
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1
    FROM CLIENTE C
    WHERE C.IDENTIFICACION  = 45632923);