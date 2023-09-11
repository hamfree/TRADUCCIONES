# Páginas y sumario

## Sumario

HonKit utiliza un archivo `SUMMARY.md` para definir la estructura de capítulos y subcapítulos del libro. El archivo `SUMMARY.md` se utiliza para generar la tabla de contenidos del libro.

El formato de `SUMMARY.md` es solo una lista de enlaces. El título del enlace se utiliza como título del capítulo y el destino del enlace es una ruta al archivo de ese capítulo.

Agregar una lista anidada a un capítulo principal creará subcapítulos.

### Ejemplo simple

```markdown
# Sumario

* [Parte I](part1/README.md)
    * [Escribir es bonito](part1/writing.md)
    * [HonKit es bonito](part1/honkit.md)
* [Parte II](part2/README.md)
    * [Nos encantan los comentarios](part2/feedback_please.md)
    * [Las mejores herramientas para los autores](part2/better_tools.md)
```

Cada capítulo tiene una página dedicada (`part#/README.md`) y está dividido en subcapítulos.

### Anclas

Los capítulos del Índice de Contenidos pueden apuntar a una parte específica de un archivo mediante un ancla.

```markdown
# Sumario

### Parte I

* [Parte I](part1/README.md)
    * [Escribir es bonito](part1/README.md#writing)
    * [HonKit es bonito](part1/README.md#honkit)
* [Part II](part2/README.md)
    * [Nos encantan los comentarios](part2/README.md#feedback)
    * [Las mejores herramientas para los autores](part2/README.md#tools)
```

### Partes

El Índice de Contenidos se puede dividir en partes separadas por títulos o líneas horizontales:

```markdown
# Sumario

### Parte I

* [Escribir es bonito](part1/writing.md)
* [HonKit es bonito](part1/honkit.md)

### Parte II

* [Nos encantan los comentarios](part2/feedback_please.md)
* [Better tools for authors](part2/better_tools.md)

----

* [Las mejores herramientas para los autores](part3/title.md)
```

Las partes son solo grupos de capítulos y no tienen páginas dedicadas, pero según el tema, se mostrará en la navegación.

## Páginas

### Sintaxis de Markdown

La mayoría de los archivos de HonKit utilizan la sintaxis Markdown de forma predeterminada. HonKit infiere la estructura de tus páginas a partir de ahí. La sintaxis utilizada es similar a la [sintaxis de GitHub Flavored Markdown] (<https://guides.github.com/features/mastering-markdown/>). También se puede optar por la [sintaxis AsciiDoc](asciidoc.md).

### ejemplo de un archivo de capítulo

``` markdown
# Título del capítulo

Esto es una gran introducción.

## Sección 1

Markdown dictará _la mayor parte_ de la **estructura de su libro**

## Sección 2

...

```

### Front Matter (Nota del traductor: se ha preferido dejarlo en inglés original)

Las páginas pueden contener una portada opcional. Se puede utilizar para definir la descripción de la página. El texto preliminar debe ser lo primero en el archivo y debe tomar la forma de YAML válido entre líneas de triple guion. Aquí hay un ejemplo básico:

```yaml
---
descripción: Esta es una breve descripción de mi página.
---

# El contenido de mi página.
...
```

La portada puede definir sus propias variables, se agregarán a la [variable de página] (templating/variables.md) para que pueda usarlas en sus plantillas.
