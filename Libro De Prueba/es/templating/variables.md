# Variables

La siguiente es una referencia de los datos disponibles durante el análisis del libro y la generación del tema.

## Variables Globales

| Variable | Descripción |
| -------- | ----------- |
| `book` | Información de todo el libro + ajustes de configuración de `book.json`. Consulte a continuación para obtener más detalles. |
| `honkit` | Información específica de HonKit |
| `gitbook` | Información específica de HonKit. Es alias de `honkit` |
| `page` | Información específica de la página actual |
| `file` | Archivo asociado con la información específica de la página actual. |
| `readme` | Información sobre el archivo LÉEME (README) |
| `glossary` | Información sobre el GLOSARIO (GLOSSARY) |
| `summary` | Información sobre el índice (SUMMARY) |
| `languages` | Lista de idiomas para libros multilingües |
| `output` | Información sobre el generador de salida. |
| `config` | Volcado del `book.json` |

## Variables del Libro (book)

| Variable | Descripción |
| -------- | ----------- |
| `book.[DATOS_DE_CONFIGURACIÓN]` | Todas las `variables` configuradas a través de `book.json` están disponibles a través de la variable book. |
| `book.language` | Idioma actual para un libro multilingüe. |

## Variables de HonKit (honkit)

| Variable | Descripción |
| -------- | ----------- |
| `honkit.time` | La hora actual (cuando ejecuta el comando `honkit`). |
| `gitbook.time` | La hora actual (cuando ejecuta el comando `honkit`). |
| `honkit.version` | Versión de HonKit utilizada para generar el libro. |
| `gitbook.version` | Versión de HonKit utilizada para generar el libro. |

## Variables de Archivo (file)

| Variable | Descripción |
| -------- | ----------- |
| `file.path` | El camino a la página sin formato. |
| `file.mtime` | Hora modificada. La última vez que se modificó el archivo |
| `file.type` | El nombre del analizador utilizado para compilar este archivo (por ejemplo: `markdown`, `asciidoc`, etc.) |

## Variables de la Página (page)

| Variable | Descripción |
| -------- | ----------- |
| `page.title` | Título de la página |
| `page.previous` | Página anterior en el Índice de Contenidos (puede ser `null`) |
| `page.next` | Página siguiente en el Índice de Contenidos (puede ser `null`) |
| `page.dir` | Dirección del texto, basada en la configuración o detectada a partir del contenido. (`rtl` o `ltr`) |

## Variables del Índice de Contenidos (summary)

| Variable | Descripción |
| -------- | ----------- |
| `summary.parts` | Lista de secciones en el Índice de Contenidos |

Se puede acceder al Índice de Contenidos completo (`SUMMARY.md`):

`summary.parts[0].articles[0].title` devolverá el título del primer artículo.

## Variable del Libro Plurilingüe (language)

| Variable | Descripción |
| -------- | ----------- |
| `languages.list` | Lista de idiomas para este libro |

Los idiomas se definen mediante `{ id: 'en', title: 'English' }`.

## Variables de salida (output)

| Variable | Descripción |
| -------- | ----------- |
| `output.name` | Nombre del generador de salida, los valores posibles son `website`, `json`, `ebook` |
| `output.format` | Cuando `output.name == "ebook"`, `format` define el formato del libro electrónico que se generará, los valores posibles son `pdf`, `epub` o `mobi` |

## Variables Readme (readme)

| Variable | Descripción |
| -------- | ----------- |
| `readme.path` | Ruta al archivo readme en el libro. |

## Variables del glosario (glossary)

| Variable | Descripción |
| -------- | ----------- |
| `glossary.path` | Ruta al glosario en el libro. |
