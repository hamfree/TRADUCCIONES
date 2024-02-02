# Plantillas

HonKit utiliza el [lenguaje de plantillas Nunjucks] (https://mozilla.github.io/nunjucks/) para procesar páginas y plantillas de temas.

La sintaxis de Nunjucks es muy similar a **Jinja2** o **Liquid**. Su sintaxis utiliza llaves `{ }` para marcar el contenido que debe procesarse.

## Variables

Una variable busca un valor en el contexto de la plantilla. Si quisiera simplemente mostrar una variable, usaría la sintaxis `{{ variable }}`. Por ejemplo:

```twig
Ni nombre es {{ nombre }}, encantado de conocerte
```

Esto busca el nombre de usuario en el contexto y lo muestra. Los nombres de las variables pueden tener puntos que buscan propiedades, al igual que JavaScript. También puede utilizar la sintaxis de corchetes.

```twig
{{ foo.bar }}
{{ foo["bar"] }}
```

Si un valor no está definido, no se muestra nada. Lo siguiente no genera nada si foo no está definido: `{{ foo }}`, `{{ foo.bar }}`, `{{ foo.bar.baz }}`.

HonKit proporciona un conjunto de [variables predefinidas](variables.md) del contexto.

## Filtros

Los filtros son esencialmente funciones que se pueden aplicar a variables. Se llaman con un operador de canalización (`|`) y pueden aceptar argumentos.

```twig
{{ foo | title }}
{{ foo | join(",") }}
{{ foo | replace("foo", "bar") | capitalize }}
```

El tercer ejemplo muestra cómo puedes encadenar filtros. Mostraría "Bar", reemplazando primero "foo" con "bar" y luego poniéndolo en mayúscula.

## Etiquetas

### if

`if` prueba una condición y le permite mostrar contenido de forma selectiva. Se comporta exactamente como se comporta el "if" de JavaScript.

```twig
{% if variable %}
  Es verdadera
{% endif %}
```

Si la variable está definida y se evalúa como verdadera, se mostrará "Es verdadera". De lo contrario, no pasará nada.

Puede especificar condiciones alternativas con `elif` y `else`:

```twig
{% if hungry %}
  Estoy hambriento
{% elif tired %}
  Estoy cansado
{% else %}
  ¡Estoy bien!
{% endif %}
```

### for

`for` itera sobre matrices y diccionarios.

```twig
# Capítulos sobre HonKit

{% for article in glossary.terms['gitbook'].articles %}
* [{{ article.title }}]({{ article.path }})
{% endfor %}
```

### set

`set` te permite crear/modificar una variable.

```twig
{% set softwareVersion = "1.0.0" %}

La versión actual es {{ softwareVersion }}.
[Descargalo](website.com/download/{{ softwareVersion }})
```

### include y block

La inclusión y herencia se detallan en la sección [Referencias de contenido](conrefs.md).

### Escapando

Si desea que HonKit ignore cualquiera de las etiquetas de plantilla especiales, puede usar `raw` y todo lo que contenga se generará como texto sin formato.

``` twig
{% raw %}
  esto {{ no será procesado }}
{% endraw %}
```
