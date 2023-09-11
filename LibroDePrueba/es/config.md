# Configuración

HonKit le permite personalizar su libro utilizando una configuración flexible. Estas opciones se especifican en un archivo `book.json`. Para los autores que no estén familiarizados con la sintaxis JSON, pueden validar la sintaxis utilizando herramientas como [JSONlint](http://jsonlint.com).

## Configuración general

| Variable | Descripción |
| -------- | ----------- |
| `root` | Ruta a la carpeta raíz que contiene todos los archivos del libro, excepto `book.json` |
| `structure` | Para especificar rutas para Readme, Summary, Glossary etc. Vea [el párrafo de estructura](#estructura). |
| `title` | Título de su libro, el valor por defecto es `HonKit`. |
| `description` | Descripción de su libro, el valor por defecto se extrae del README. |
| `author` | Nombre del autor(es), varios autores deben estar separados por el símbolo ampersand (&) |
| `authorSort` | Cadena que se utilizará al ordenar por autor. |
| `producer` | Nombre del productor. |
| `publisher` | Nombre del editor. |
| `series` | Serie a la que pertenece este libro. |
| `seriesIndex` | Índice del libro de esta serie. |
| `pubdate` | Fecha de publicación del libro, formateada como AAAA-MM-DDTHH: MM: SS. |
| `isbn` | ISBN del libro |
| `language` | [Código ISO](https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) del idioma del libro, el valor predeterminado es `en` |
| `direction` | Dirección del texto. Puede ser `rtl` o `ltr`, el valor por defecto depende del valor de `language` |
| `gitbook` | Versión de HonKit que se debe utilizar. Utiliza la especificación [SemVer](http://semver.org) y acepta condiciones como `">= 3.0.0"` |
| `honkit` | Versión de HonKit que se debe utilizar. Utiliza la especificación [SemVer](http://semver.org) y acepta condiciones como `">= 3.0.0"` |

### Complementos

Los complementos y sus configuraciones se especifican en `book.json`. Consulte [Complementos](plugins/README.md) para obtener más detalles.

| Variable | Descripción |
| -------- | ----------- |
| `plugins` | Lista de complementos a cargar |
| `pluginsConfig` |Configuración para los complementos |

### Estructura

Además de la variable `root`, puede decirle a HonKit el nombre de los archivos Readme, Summary, Glossary y Lnaguages (en lugar de usar los nombres predeterminados como `README.md`).
Estos archivos deben estar en la raíz de su libro (o en la raíz de cada libro de idioma). No se aceptan rutas como `dir/MY_README.md`.

| Variable | Descripción |
| -------- | ----------- |
| `structure.readme` | nombre del archivo Readme (por defecto es `README.md`) |
| `structure.summary` | nombre del archivo Summary (por defecto es `SUMMARY.md`) |
| `structure.glossary` | nombre del archivo Glossary (por defecto es `GLOSSARY.md`) |
| `structure.languages` | nombre del archivo Languages (por defecto es `LANGS.md`) |

### Opciones para PDF

La salida PDF se puede personalizar usando un conjunto de opciones en `book.json`:

| Variable | Descripción |
| -------- | ----------- |
| `pdf.pageNumbers` | Agregue números de página al final de cada página (el valor predeterminado es `true`) |
| `pdf.fontSize` | Tamaño de fuente base (el valor predeterminado es `12`) |
| `pdf.fontFamily` | Familia de fuentes base (el valor predeterminado es `Arial`) |
| `pdf.paperSize` | Tamaño del papel, las opciones son `'a0', 'a1', 'a2', 'a3', 'a4', 'a5', 'a6', 'b0', 'b1', 'b2', 'b3', 'b4', 'b5', 'b6', 'legal', 'letter'` (el valor predeterminado es `a4`) |
| `pdf.margin.top` | Margen superior (el valor predeterminado es `56`) |
| `pdf.margin.bottom` | Margen inferior (el valor predeterminado es `56`) |
| `pdf.margin.right` | Margen derecho (el valor predeterminado es `62`) |
| `pdf.margin.left` | Margen izquierdo (el valor predeterminado es `62`) |
| `pdf.embedFonts` | Embeber todas las fuentes dentro del PDF (el valor predeterminado es `false`) |
