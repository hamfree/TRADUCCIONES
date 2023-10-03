# Tematización

Desde la versión 3.0.0, HonKit se puede personalizar fácilmente. Los libros usan el tema [theme-default](https://github.com/honkit/honkit/tree/master/packages/%40honkit/theme-default) de forma predeterminada.

> **Precaución**: la temática personalizada puede impedir que algunos complementos funcionen correctamente.

## Estructura de un tema

Un tema es un complemento que contiene plantillas y recursos. Anular cualquier plantilla individual es opcional, ya que los temas siempre amplían el tema predeterminado.

| Carpeta | Descripción |
| -------- | ----------- |
| `_layouts` | Carpeta principal que contiene todas las plantillas. |
| `_layouts/website/page.html` | Plantilla para una página normal. |
| `_layouts/ebook/page.html` | Plantilla para una página normal durante la generación de libros electrónicos (PDF< ePub, Mobi) |

## Ampliar/Personalizar tema en un libro

Los autores pueden ampliar las plantillas de un tema directamente desde la fuente de su libro (sin crear un tema externo). Las plantillas se resolverán primero en la carpeta `_layouts` del libro y luego en los complementos/temas instalados.

## Extender en lugar de bifurcar

Cuando desee que los cambios de tema estén disponibles para varios libros, en lugar de bifurcar el tema predeterminado, puede extenderlo usando la [sintaxis de plantilla](../templating/README.md):

```html
{% extends template.self %}

{% block body %}
    {{ super() }}
    ... Esto se agregará al bloque "body".
{% endblock %}
```

Eche un vistazo al tema [API](https://github.com/GitbookIO/theme-api) para ver un ejemplo más completo.

## Publicar un tema

Los temas se publican como complementos ([ver documentos relacionados](../plugins/README.md)) con un prefijo `theme-`. Por ejemplo, el tema `awesome` se cargará desde el complemento `theme-awesome` y luego desde el paquete NPM `honkit-plugin-theme-awesome`.
