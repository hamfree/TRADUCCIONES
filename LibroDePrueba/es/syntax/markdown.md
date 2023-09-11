# Markdown

La mayoría de los ejemplos de esta documentación están en Markdown. Markdown es el analizador predeterminado para HonKit, pero también se puede optar por la [sintaxis AsciiDoc](asciidoc.md).

Aquí hay una descripción general de la sintaxis de Markdown que puede usar con HonKit (igual que GitHub con algunas adiciones).

## Encabezamientos {#headings}

Para crear un encabezado, agregue de uno a seis símbolos `#` antes del texto del encabezado. El número de # que utilice determinará el tamaño del título.

```markdown
# Esta es una etiqueta <h1> 
## Esta es una etiqueta <h2>
###### Esta es una etiqueta <h6>
```

HonKit admite una buena forma de configurar explícitamente el ID del encabezado. Si sigue el texto del encabezado con una llave de apertura (separada del texto con al menos un espacio), un hash, el ID y una llave de cierre, el ID se establece en el encabezado. Si utiliza la función de hash final de los encabezados de estilo atx, el ID del encabezado debe ir después de los hashes finales. Por ejemplo:

```markdown
Hola {#id}
-----

# Hola {#id}

# Hola # {#id}
```

## Párrafos y saltos de línea {#paragraphs}

Un párrafo es simplemente una o más líneas de texto consecutivas, separadas por una o más líneas en blanco. (Una línea en blanco es cualquier línea que parece una línea en blanco; una línea que no contiene nada más que espacios o tabulaciones se considera en blanco). Los párrafos normales no deben tener sangrías ni espacios ni tabulaciones.

```markdown
Aquí tenemos una línea para empezar.

Esta línea está separada de la anterior por dos nuevas líneas, por lo que será un *párrafo separado*.
```

## Énfasis {#emphasis}

```markdown
*Este texto estará en cursiva.*
_Esto también estará en cursiva._

**Este texto estará en negrita.**
__Esto también será en negrita.__

~~Este texto será tachado..~~

_Puedes ** combinarlos_
```

## Listas {#lists}

Markdown admite listas ordenadas (numeradas) y desordenadas (con viñetas).

### Desordenadas

Las listas desordenadas utilizan asteriscos, signos más y guiones (indistintamente) como marcadores de lista:

```markdown
* Elemento 1
* Elemento 2
  * Elemento 2a
  * Elemento 2b
```

### Ordenadas

Las listas ordenadas utilizan números seguidos de puntos.:

```markdown
1. Elemento 1
2. Elemento 2
3. Elemento 3
   * Elemento 3a
   * Elemento 3b
```

## Enlaces {#links}

Markdown admite dos estilos de enlaces: en línea y de referencia.

Se puede crear un enlace simple encerrando el texto entre corchetes y la URL del enlace entre paréntesis:

```markdown
Este es un enlace en línea [un ejemplo](http://example.com/ "Título") con un título.

[Este enlace](http://example.net/) no tiene atributo de título.
```

Los enlaces pueden apuntar a rutas relativas, anclajes o URL absolutas.

## Referencias

Existe otra forma de crear enlaces que no interrumpe el flujo del texto. La URL y el título se definen mediante un nombre de referencia y este nombre de referencia se utiliza entre corchetes en lugar de la URL del enlace:

```markdown
Este es un enlace de estilo de referencia [un ejemplo][id].
```

Luego, en cualquier parte del documento, define la etiqueta de su enlace de esta manera, en una línea separada:

```markdown
[id]: http://example.com/  "Título opcional aquí"
```

## Imágenes {#images}

Las imágenes se pueden crear de forma similar a los enlaces: simplemente utilice un signo de exclamación antes de los corchetes. El texto del enlace se convertirá en el texto alternativo de la imagen y la URL del enlace especifica la fuente de la imagen:

```markdown
Una imagen: ![gras](img/image.jpg)
```

## Citas en bloque {#blockquotes}

Una cita en bloque se inicia usando el marcador `>` seguido de un espacio opcional; todas las líneas siguientes que también comienzan con el marcador de cita en bloque pertenecen a la cita en bloque. Puedes usar cualquier elemento a nivel de bloque dentro de una cita en bloque:

```markdown
Como dijo Kanye West:

> Estamos viviendo el futuro por lo que 
> el presente es nuestro pasado.
```

## Tablas {#tables}

Puede crear tablas reuniendo una lista de palabras y dividiéndolas con guiones `-` (para la primera fila) y luego separando cada columna con una barra vertical `|`:

```markdown
| Primera Cabecera  | Segunda Cabecera |
| ------------- | ------------- |
| Celda de contenido  | Celda de contenido  |
| Celda de contenido  | Celda de contenido  |
```

Los tubos en cada extremo de la tabla son opcionales. Las celdas pueden variar en ancho y no es necesario que estén perfectamente alineadas dentro de las columnas. Debe haber al menos tres guiones en cada columna de la fila del encabezado.

## Código {#code}

Markdown admite dos estilos de bloques de código diferentes. Uno usa líneas con sangría con cuatro espacios o una tabulación, mientras que el otro usa líneas con caracteres de tilde como delimitadores; por lo tanto, no es necesario sangrar el contenido:

```markdown
Este es un bloque de código de muestra.

    Continúa aquí.

```

### Bloques de código vallados

Puede crear bloques de código delimitados colocando comillas invertidas triples ` ``` ` antes y después del bloque de código. Recomendamos colocar una línea en blanco antes y después de los bloques de código para que el formato crudo sea más fácil de leer.

```javascript
function test() {
  console.log("¿Notas la línea en blanco antes de esta función?");
}
```

### Resaltado de sintaxis

Puede agregar un identificador de idioma opcional para habilitar el resaltado de sintaxis en su bloque de código delimitado.

Por ejemplo, para resaltar la sintaxis del código Ruby:

```ruby
require 'redcarpet'
markdown = Redcarpet.new("¡Hola mundo!")
puts markdown.to_html
```

### Código en línea

Las frases de texto se pueden marcar como código rodeándolas con comillas invertidas:

```text
    Use `honkit` parar convertir el `texto` en sintaxis markdown
    a HTML.
```

## HTML

HonKit admite el uso de HTML sin formato en su texto, la sintaxis de Markdown en HTML no se procesa:

```html
<div>
El Markdown aquí no será **analizado**
</div>
```

### Regla horizontal

Las reglas horizontales se pueden insertar usando tres o más asteriscos, guiones o guiones bajos, opcionalmente separados por espacios o tabulaciones, en una línea que de otro modo estaría en blanco:

```markdown
Tres o más...

---

Guiones

***

Asteriscos

```

### Ignorar el formato Markdown

Puede indicarle a HonKit que ignore (o escape) el formato de Markdown usando `\` antes del carácter Markdown.

```text
Cambiemos el nombre de \*nuestro-nuevo-proyecto\* a \*nuestro-antiguo-proyecto\*.
```

## Notas a pie de página {#footnotes}

HonKit admite una sintaxis simple para este tipo de notas a pie de página. Las notas a pie de página son relativas a cada página.

```markdown
Texto anterior a la referencia a la nota a pie de página.[^2]

[^2]: Comentario a incluir en nota al pie.
```
