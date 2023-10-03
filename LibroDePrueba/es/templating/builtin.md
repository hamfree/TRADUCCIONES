# Ayudantes de plantillas incorporados

HonKit proporciona una serie de filtros y bloques integrados para ayudarte a escribir plantillas.

## Filtros

`value|default(default, [boolean])`
Si el valor no está estrictamente definido, devuelve el valor predeterminado; en caso contrario, el valor. Si booleano es verdadero, cualquier valor falso de JavaScript devolverá el valor predeterminado (false, "", etc.)

`arr|sort(reverse, caseSens, attr)`
Ordene arr con la función arr.sort de JavaScript. Si lo contrario es cierto, el resultado se invertirá. La clasificación no distingue entre mayúsculas y minúsculas de forma predeterminada, pero establecer caseSens en verdadero hace que sí lo haga. Si se pasa el atributo (attr), se comparará el atributo de cada elemento.

## Bloques

`{% markdown %}Markdown string{% endmarkdown %}`
Representa markdown en línea

`{% asciidoc %}AsciiDoc string{% endasciidoc %}`
Representa asciidoc en línea
