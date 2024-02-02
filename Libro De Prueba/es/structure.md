# Estructura de directorios

HonKit utiliza una estructura de directorios simple. Todos los archivos Markdown/Asciidoc enumerados en la sección [Páginas_y_sumario] (pages.md) se transformarán como HTML. Los libros multilingües tienen una estructura ligeramente [diferente] (languages.md).

Un HonKit básico suele verse así:

```text
.
├── book.json
├── README.md
├── SUMMARY.md
├── capitulo-1/
|   ├── README.md
|   └── algo.md
└── capitulo-2/
    ├── README.md
    └── algo.md
```

Una descripción general de lo que hace cada uno de estos:

| Archivo | Descripción |
| -------- | ----------- |
| `book.json` | Almacena datos de [configuración](config.md) data (__opcional__) |
| `README.md` | Prefacio / Introducción de su libro (__obligatorio__) |
| `SUMMARY.md` | Índice de Contenidos (Vea [Páginas_y_sumario](pages.md)) (__opcional__) |
| `GLOSSARY.md` | Léxico / Lista de términos para anotar (Vea [Glossary](lexicon.md)) (__opcional__) |

## Archivos estáticos e imágenes

Un archivo estático es un archivo que no figura en `SUMMARY.md`. Todos los archivos estáticos, a menos que se [ignoren](#ignore), se copian en la salida.

## Ignorar archivos y carpetas {#ignore}

HonKit leerá los archivos `.gitignore`, `.bookignore` y `.ignore` para obtener una lista de archivos y carpetas que se deben omitir.
El formato dentro de esos archivos sigue la misma convención que `.gitignore`:

```text
# Esto es un comentario

# Ignora el archivo test.md
test.md

# Ignora todo en el directorio "bin"
bin/*
```

### Integración del proyecto con el subdirectorio {#subdirectory}

Para proyectos de software, puede utilizar un subdirectorio (como `docs/`) para almacenar el libro de documentación del proyecto. Puede configurar la [opción `root`](config.md) para indicar la carpeta donde HonKit puede encontrar los archivos del libro:

```text
.
├── book.json
└── docs/
    ├── README.md
    └── SUMMARY.md
```

Con `book.json` que contiene:

```json
{
    "root": "./docs"
}
```
