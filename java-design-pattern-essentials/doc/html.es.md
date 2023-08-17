[Página de inicio de Plantilla HTML5](https://html5boilerplate.com/) | [Índice de la documentación](TOC.es.md)

# El HTML

Por defecto, la Plantilla HTML5 proporciona dos páginas `html`:

* [`index.html`](#indexhtml) - un esqueleto HTML predeterminado que debe formar la base de todas las páginas de su
sitio web
* `404.html` - una página de error 404 de marcador de posición

## `index.html`

### La Clase `no-js`

La clase `no-js` se proporciona para permitirle agregar más fácilmente y de forma explícita estilos personalizados en
función de si JavaScript está deshabilitado (`.no-js`) o habilitado (`.js`). El uso de esta técnica también ayuda a
[evitar la FOUC](https://www.paulirish.com/2009/avoiding-the-fouc-v3/).

### Atributo de idioma

Por favor, considera especificar el idioma de tu contenido agregando un [valor](https://www.iana.org/assignments/language-subtag-registry/language-subtag-registry) al atributo `lang` en la etiqueta
`<html>` como en este ejemplo:

```html
<html class="no-js" lang="es">
```

### El orden de las etiquetas `<title>` y `<meta>`

La declaración del conjunto de caracteres (`<meta charset="utf-8">`) debe estar incluída completamente dentro de los
[1024 bytes primeros del documento](https://html.spec.whatwg.org/multipage/semantics.html#charset) y debe especificarse lo antes posible (antes de cualquier contenido que pueda
ser controlado por un atacante, como un elemento `<título>`) para evitar un posible
[problema de seguridad relacionado con la codificación](https://code.google.com/archive/p/doctype-mirror/wikis/ArticleUtf.wiki) en Internet Explorer.

### Etiqueta Meta Description

La etiqueta meta `description` proporciona una descripción corta de la página. En algunas situaciones esta descripción
se usa como parte del fragmento mostrado en los resultados de búsqueda.

```html
<meta name="description" content="Esto es una descripción">
```

[Crea buenas metadescripciones](https://support.google.com/webmasters/answer/35624?hl=en#meta-descriptions) de la documentación de Google tiene consejos útiles para crear una descripción
eficaz.

### Etiqueta Meta Mobile Viewport

Hay algunas opciones diferentes que puede usar con la
[metaetiqueta `viewport`](https://docs.google.com/present/view?id=dkx3qtm_22dxsrgcf4 "Viewport y
Media Queries - The Complete Idiot's Guide"). Puede obtener más información en [los documentos web de MDN](https://developer.mozilla.org/en-US/docs/Mozilla/Mobile/Viewport_meta_tag).
La Plantilla HTML5 viene con una configuración simple que logra un buen equilibrio para los casos de uso general.

```html
<meta name="viewport" content="width=device-width, initial-scale=1">
```
Si desea aprovechar las pantallas de borde a borde del iPhone X/XS/XR, puede hacerlo con unos parámetros de  ventana de
visualización adicionales. [Consulte el blog de WebKit](https://webkit.org/blog/7929/designing-websites-for-iphone-x/) para obtener más detalles.


### Metadatos Open Graph

El [Protocolo Open Graph](https://ogp.me/) le permite definir la forma en que se presenta su sitio cuando se hace
referencia a sitios y aplicaciones de terceros (Facebook, Twitter, LinkedIn). El protocolo proporciona una serie de
metaelementos que definen los detalles de su sitio. Los atributos requeridos definen el título, la imagen de vista
previa, la URL y [el tipo](https://ogp.me/#types) (por ejemplo, video, música, sitio web, artículo).

``` html
<meta property="og:title" content="">
<meta property="og:type" content="">
<meta property="og:url" content="">
<meta property="og:image" content="">
```

Además de estos cuatro atributos, hay muchos más atributos que puede usar para agregar más riqueza a la descripción de
su sitio. Esto solo representa la implementación más básica.

Para ver un ejemplo de trabajo, los siguientes son los metadatos de gráficos abiertos para el sitio Plantilla HTML5.
Además de los campos obligatorios, agregamos `og:description` para describir el sitio con más detalle.

``` html
<meta name="og:url" content="https://html5boilerplate.com/">
<meta name="og:title" content="HTML5 ★ BOILERPLATE">
<meta name="og:type" content="website">
<meta name="og:description" content="La plantilla de front-end más popular de la web que lo ayuda a crear aplicaciones o sitios web rápidos, robustos y adaptables.">
<meta name="og:image" content="https://html5boilerplate.com/icon.png">
```

### Manifiesto de la aplicación web

La Plantilla HTML5 incluye un archivo de manifiesto de aplicación web simple.

El manifiesto de la aplicación web es un archivo JSON simple que le permite controlar cómo aparece su aplicación en la
pantalla de inicio de un dispositivo, cómo se ve cuando se inicia en ese contexto y qué sucede cuando se inicia. Esto
permite un control mucho mayor sobre la interfaz de usuario de un sitio guardado o una aplicación web en un dispositivo
móvil.

Está vinculado desde el HTML de la siguiente manera:

```html
<link rel="manifest" href="site.webmanifest">
```
Nuestro [site.webmanifest](https://github.com/h5bp/html5-boilerplate/blob/master/src/site.webmanifest) contiene una
definición de "aplicación" muy básica, solo para mostrar el uso básico. Debe completar este archivo con
[más información sobre su sitio o aplicación](https://developer.mozilla.org/en-US/docs/Web/Manifest)


### Favicons y Touch Icon

Los íconos de acceso directo deben colocarse en el directorio raíz de su sitio.
`favicon.ico` es recogido automáticamente por los navegadores si se coloca en la raíz. La Plantilla HTML5 viene con un
conjunto predeterminado de íconos (incluye favicon y un ícono Apple Touch) que puede usar como referencia para crear el
suyo propio.

Consulte la descripción más detallada en la [sección Extender](extend.md) de estos documentos.

### El área de contenido

La parte central de la plantilla repetitiva está prácticamente vacía. Esto es intencional, con el fin de hacer que el
modelo sea adecuado tanto para el desarrollo de páginas web como de aplicaciones web.

### Modernizr

La Plantilla HTML5 utiliza una compilación personalizada de Modernizr.

[Modernizr](https://modernizr.com/) es una biblioteca de JavaScript que agrega clases al elemento `html` en función de
los resultados de la prueba de características y que asegura que todos los navegadores puedan hacer uso de elementos
HTML5 (ya que incluye HTML5 Shiv). Esto te permite dirigir partes de tu CSS y JavaScript en función de las funciones
compatibles con un navegador.

A partir de la versión 3, Modernizr se puede personalizar mediante [modernizr-config.json](https://github.com/h5bp/html5-boilerplate/blob/master/modernizr-config.json) y la
[utilidad de línea de comandos de Modernizr](https://www.npmjs.com/package/modernizr-cli).

### ¿Qué pasa con los polyfills?

Si necesita incluir [polyfills](https://remysharp.com/2010/10/08/what-is-a-polyfill) en su proyecto, debe asegurarse de
que se carguen antes que cualquier otro JavaScript. Si está utilizando un servicio CDN de polyfill, como
[polyfill.io](https://polyfill.io/v3/), colóquelo antes de los otros scripts en la parte inferior de la página:

```html
    <script src="js/vendor/modernizr-3.10.0.min.js"></script>
    <script src="https://polyfill.io/v3/polyfill.min.js"></script>
    <script src="js/plugins.js"></script>
    <script src="js/main.js"></script>
</body>
```
Si desea incluir los polyfills usted mismo, puede incluirlos en `js/plugins.js`. Cuando tenga un montón de polyfills
para cargar, también puede crear un archivo `polyfills.js` en el directorio `js/vendor` o incluir los archivos
individualmente y combinarlos usando una herramienta de compilación. Siempre asegúrese de que todos los polyfills estén
cargados antes que cualquier otro JavaScript.

Hay algunos conceptos erróneos sobre Modernizr y polyfills. Es importante comprender que Modernizr solo maneja la
verificación de funciones, no el polyfill en sí. Lo único que hace Modernizr con respecto a los polyfills es que el
equipo mantiene [una enorme lista de polyfills entre navegadores](https://github.com/Modernizr/Modernizr/wiki/HTML5-Cross-Browser-Polyfills).

### jQuery

A partir de la versión 8.0.0, ya no incluimos jQuery de forma predeterminada. El desarrollo web ha cambiado mucho desde
que comenzamos este proyecto y, aunque muchos millones de sitios aún usan jQuery, hay muchos sitios y aplicaciones que
no lo hacen. Hace 10 años jQuery _era_ JavaScript para la mayoría de los desarrolladores. Ese ya no es el caso, por lo
que hemos tomado la decisión de eliminar jQuery del proyecto.

Si estás interesado en incluirlo, puedes instalar fácilmente jQuery usando el siguiente comando:

```
npm install jQuery
```
Luego puede copiar el archivo minimizado en la carpeta `provider` y agregar jQuery a `index.html` manualmente.

Para cargar jQuery desde un CDN con un respaldo local, puede usar lo siguiente:

``` html
<script src="https://code.jquery.com/jquery-3.5.1.min.js"  integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<script>window.jQuery || document.write('<script src="js/vendor/jquery-3.5.1.min.js"><\/script>')</script>
```

### Código de seguimiento de Google Universal Analytics

Finalmente, se incluye una versión optimizada del código de seguimiento de Google Universal Analytics.

Usamos `analytics.js` en lugar del nuevo `gtag.js` porque [es más rápido y admite tareas y complementos](https://github.com/philipwalton/analyticsjs-boilerplate/issues/19#issuecomment-333714370)

A partir de la versión 8.0.0, de manera predeterminada, [anonimizamos las direcciones IP](https://support.google.com/analytics/answer/2763052). De forma predeterminada,
Google Analytics registra la dirección IP completa de un usuario que visita el sitio, pero esa dirección IP completa
nunca está disponible para el administrador de la propiedad de Google Analytics. Al anonimizar la dirección IP, puede
hacer que su sitio cumpla más con el RGPD, ya que una dirección IP completa se puede definir como PII (información de
identificación personal).

El mecanismo de transporte de balizas se utiliza para enviar todas las visitas
[lo que guarda las solicitudes HTTP y mejora el rendimiento](https://philipwalton.com/articles/the-google-analytics-setup-i-use-on-every-site-i-compilación/#loading-analytics.js).

Google recomienda que este script se coloque en la parte superior de la página. Factores a tener en cuenta: si coloca
este script en la parte superior de la página, podrá contar los usuarios que no cargan completamente la página e
incurrirá en el número máximo de conexiones simultáneas del navegador.

Tenga en cuenta que, si bien Google [afirma que cumple totalmente con el RGPD](https://privacy.google.com/businesses/compliance/), aún es posible usar análisis para
violar el RGPD.

Más información:

* [Introducción a Analytics.js](https://developers.google.com/analytics/devguides/collection/analyticsjs/)
* [Demostraciones y herramientas de Google Analytics](https://ga-dev-tools.appspot.com/)
* [Controles de privacidad en Google Analytics](https://support.google.com/analytics/answer/9019185)

**N.B.** El fragmento de Google Analytics se incluye de forma predeterminada principalmente porque Google Analytics es
[actualmente una de las soluciones de seguimiento más populares](https://trends.builtwith.com/analytics/Google-Analytics) que existe.
Sin embargo, su uso no está escrito en piedra, y DEBE considerar explorar las [alternativas](https://en.wikipedia.org/wiki/List_of_web_analytics_software) y usar lo que mejor se
adapte a sus necesidades.
