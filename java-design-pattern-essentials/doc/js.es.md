[Página de inicio de Plantilla HTML5](https://html5boilerplate.com/) | [Índice de la documentación](TOC.es.md)

# El JavaScript

Información sobre el JavaScript predeterminado incluido en el proyecto.

## main.js

Este archivo se puede usar para contener o hacer referencia al código JavaScript de su sitio/aplicación. Si está
trabajando en algo más avanzado, puede reemplazar este archivo por completo. Eso es genial.

## plugins.js

Este archivo se puede usar para contener todos sus complementos, como complementos de jQuery y otros scripts de terceros
para un sitio simple.

Un enfoque es colocar los complementos de jQuery dentro de un cierre `(function($){ ...})(jQuery);` para asegurarse de
que estén en la manta de seguridad del espacio de nombres de jQuery.
Obtenga más información sobre [la creación de complementos de jQuery](https://learn.jquery.com/plugins/).

De forma predeterminada, el archivo `plugins.js` contiene un pequeño script para evitar errores de `console` en los
navegadores que carecen de `console`. El script se asegurará de que, si un método de consola no está disponible, ese
método tendrá el valor de función vacía, evitando así que el navegador arroje un error.

## vendor

Este directorio se puede utilizar para contener todo el código de biblioteca de terceros.

Nuestra compilación personalizada de la biblioteca Modernizr se incluye de forma predeterminada. Es posible que desee
crear su propia [construcción personalizada de Modernizr con el generador en línea](https://modernizr.com/download/) o
[herramienta de línea de comandos](https://modernizr.com/docs#command-line-config).
