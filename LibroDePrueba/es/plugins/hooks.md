# Ganchos

Los ganchos son un método de aumentar o alterar el comportamiento del proceso, con retrollamadas personalizadas.

## Lista de ganchos

### Relativos a la tubería global

| Nombre | Descripción | Argumentos |
| ---- | ----------- | --------- |
| `init` | Se llama después de analizar el libro, antes de generar resultados y páginas. | Ninguno |
| `finish:before` | Se llama después de generar las páginas, antes de copiar activos, portada,... | Ninguno |
| `finish` | Llamado después de todo lo demás. | Ninguno |

### Relativos a la tubería de la página

> Se recomienda utilizar [plantillas](./templating.md) para ampliar el análisis de la página.

| Nombre | Descripción | Argumentos |
| ---- | ----------- | --------- |
| `page:before` | Llamado antes de ejecutar el motor de plantillas en la página. | El objeto Page |
| `page` | Se llama antes de generar e indexar la página. | El objeto Page |

:memo: HonKit puede omitir estos enlaces de páginas en una página sin cambios cuando está en modo incremental (`honkit server`)

#### Objeto Page

```js
{
    // Analizador llamado
    "type": "markdown",

    // Ruta del archivo relativa a la raíz del libro
    "path": "page.md",

    // Ruta absoluta del archivo
    "rawpath": "/usr/...",

    // Título de la página en el RESUMEN
    "title": "",

    // Contenido de la página
    // Markdown/Asciidoc en "page:before"
    // HTML en "page"
    "content": "<h1>Hello</h1>"

    // Nivel de la pagina
    "level": "1.5.3.1"

    // Profundidad de la página
    "depth": "3"

    // Otros atributos aparecen en el .md entre dos '---' al comienzo del contenido
    // Por ejemplo, al frente del markdown:
    // ---
    // description: Esto es una descripción
    // ---
    "description": "Esto es una descripción"

    // Artículo anterior
    "previous": Article Object

    // Artículo siguiente
    "next": Article Object
}
```

##### Ejemplo para agregar un título

En el gancho `page:before`, `page.content` es el contenido de markdown/asciidoc.

```js
{
    "page:before": function(page) {
        page.content = "# Título\n" +page.content;
        return page;
    }
}
```

##### Ejemplo para reemplazar algunas páginas html

En el gancho `page`, `page.content` es el HTML generado a partir de la conversión markdown/asciidoc.

```js
{
    "page": function(page) {
        page.content = page.content.replace("<b>", "<strong>")
            .replace("</b>", "</strong>");
        return page;
    }
}
```

### Operaciones asincrónicas

Las retrollamadas de los ganchos pueden ser asincrónicas y devolver promesas (objetos promises de JavaScript).

Ejemplo:

```js
{
    "init": function() {
        return writeSomeFile()
        .then(function() {
            return writeAnotherFile();
        });
    }
}
```
