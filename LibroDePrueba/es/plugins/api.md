# Contexto e IPA

HonKits proporciona diferentes API y contextos para los complementos. Estas API pueden variar según la versión de HonKit que se utilice; su complemento debe especificar el campo `engines.gitbook` en `package.json` en consecuencia.

## Instancia Book

La clase `Book` es el punto central de HonKit, centraliza todos los métodos de lectura de acceso. Esta clase está definida en [book.js](https://github.com/honkit/honkit/blob/master/lib/book.js).

```js
// Lee la configuración de book.json
var value = book.config.get('title', 'Valor predeterminado');

// Resolve a filename to an absolute path
var filepath = book.resolve('README.md');

// Render an inline markup string
book.renderInline('markdown', 'This is **Markdown**')
    .then(function(str) { ... })

// Render a markup string (block mode)
book.renderBlock('markdown', '* This is **Markdown**')
    .then(function(str) { ... })
```

#### Output instance

La clase `Output` representa el proceso de salida/escritura.

```js
// Devuelve la carpeta raíz para la salida
var root = output.root();

// Resuelve un archivo en la carpeta de salida
var filepath = output.resolve('miimagen.png');

// Convierte un nombre de archivo en una URL (devuelve una ruta a un archivo html)
var fileurl = output.toURL('micapitulo/README.md');

// Escribe un archivo en la carpeta de salida
output.writeFile('hola.txt', 'Hola Mundo')
    .then(function() { ... });

// Copia un archivo a la carpeta de salida
output.copyFile('./miarchivo.jpg', 'portada.jpg')
    .then(function() { ... });

// Verfica que un archivo existe
output.hasFile('hola.txt')
    .then(function(exists) { ... });
```

## Instancia Page

Una instancia page representa la página actualmente analizada.

```js
// Título de la página (del  SUMMARY)
page.title

// Contenido de la página (Markdown/Asciidoc/HTML según la etapa)
page.content

// Ruta relativa en el libro.
page.path

// Ruta absoluta al archivo.
page.rawPath

// Tipo de analizador utilizado para este archivo
page.type ('markdown' or 'asciidoc')
```

## Contexto para bloques y filtros

Los bloques y filtros tienen acceso al mismo contexto, este contexto está vinculado a la ejecución del motor de plantillas:

```js
{
    // Sintaxis de plantillas actual
    "ctx": {
        // Por ejemplo, después de un {% set message = "hello" %}
        "message": "hello"
    },

    // instancia Book
    "book" <Book>,

    // instancia Output
    "output": <Output>
}
```

Por ejemplo, una función de filtro o bloqueo puede acceder al libro actual usando: `this.book`.

## Contexto para los ganchos

Los ganchos solo tienen acceso a la instancia `<Book>` usando `this.book`.
