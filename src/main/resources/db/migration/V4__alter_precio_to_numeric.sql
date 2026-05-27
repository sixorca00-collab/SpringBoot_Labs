-- V4: Alinear tipo de precio con la entidad Libro (BigDecimal)
ALTER TABLE libros
    ALTER COLUMN precio NUMERIC(38,2);
