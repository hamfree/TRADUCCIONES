# IPA & Contexto

HonKits proporciona diferentes API y contextos para los complementos. Estas API pueden variar según la versión de HonKit que se utilice; su complemento debe especificar el campo `engines.gitbook` en `package.json` en consecuencia.

## La instancia Book

La clase `Book` es el punto central de HonKit, centraliza todos los métodos de lectura de acceso. Esta clase se define en [book.js](https://github.com/honkit/honkit/blob/master/lib/book.js).

```js
// Lee la configuración de book.json
var value = book.config.get('title', 'Valor predeterminado');

// Resolver un nombre de archivo en una ruta absoluta
var filepath = book.resolve('README.md');

// Representar una cadena de marcado en línea
book.renderInline('markdown', 'Esto es **Markdown**')
    .then(function(str) { ... })

// Representar una cadena de marcado (modo bloque)
book.renderBlock('markdown', '* Esto es **Markdown**')
    .then(function(str) { ... })
```

## La instancia Output

La clase `Output` representa el proceso de salida/escritura.

```js
// Devolver la carpeta raíz para la salida
var root = output.root();

// Resolver un archivo en la carpeta de salida
var filepath = output.resolve('miimagen.png');

// Convertir un nombre de archivo en una URL (devuelve una ruta a un archivo html)
var fileurl = output.toURL('micapitulo/LEEME.md');

// Escribe un archivo en la carpeta de salida.
output.writeFile('hola.txt', 'Hola mundo')
    .then(function() { ... });

// Copiar un archivo a la carpeta de salida
output.copyFile('./mifichero.jpg', 'portada.jpg')
    .then(function() { ... });

// Verificar que existe un archivo
output.hasFile('hola.txt')
    .then(function(exists) { ... });
```

## La instancia Page

Una instancia de page representa la página analizada actual.

```js
// Título de la página (del RESUMEN)
page.title

// Contenido de la página (Markdown/Asciidoc/HTML según la etapa)
page.content

// Camino relativo en el libro.
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
        // Por ejemplo, después de un {% set message = "hola" %}
        "message": "hola"
    },

    // Instancia de Book
    "book" <Book>,

    // Instancia de Output
    "output": <Output>
}
```

Por ejemplo, una función de filtro o bloqueo puede acceder al libro actual usando: `this.book`.

## Contexto para ganchos

Los ganchos solo tienen acceso a la instancia `<Book>` usando `this.book`.
