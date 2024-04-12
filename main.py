from faker import Faker
import random

fake = Faker()

# Generar 100 registros aleatorios
registros = []
for i in range(100):
    grado_id = random.randint(1, 9)  # Seleccionar un grado aleatorio entre 1 y 9
    email = fake.email()
    apellido = fake.last_name()
    nombre = fake.first_name()
    registros.append((grado_id, i+1, email, apellido, nombre))

# Generar la consulta SQL
sql_query = "INSERT INTO `estudiantes` (`grado_id`, `id`, `email`, `apellido`, `nombre`) VALUES "
for registro in registros:
    sql_query += f"('{registro[0]}', '{registro[1]}', '{registro[2]}', '{registro[3]}', '{registro[4]}'),"
sql_query = sql_query[:-1]  # Eliminar la coma extra al final

print(sql_query)
