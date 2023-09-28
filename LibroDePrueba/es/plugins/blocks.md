# Extender bloques

Ampliar los bloques de plantillas es la mejor manera de proporcionar funcionalidades adicionales a los autores.

El uso más común es procesar el contenido de algunas etiquetas en tiempo de ejecución. Es como los [filtros](./filters.md), pero con esteroides porque no estás limitado a una sola expresión.

## Definiendo un nuevo bloque

Los bloques están definidos por el complemento, los bloques son un mapa de nombre asociado con un descriptor de bloque. El descriptor de bloque debe contener al menos un método `process`.

```js
module.exports = {
    blocks: {
        tag1: {
            process: function(block) {
                return "Hola "+block.body+", ¿Cómo estás?";
            }
        }
    }
};
```

El `process` debería devolver el contenido html que reemplazará la etiqueta. Consulte [Contexto y API] (./api.md) para obtener más información sobre `this` y la API HonKit.

## Manejo de argumentos de bloque

Los argumentos se pueden pasar a bloques:

```twig
{% tag1 "argumento 1", "argumento 2", name="Prueba" %}
Este es el cuerpo del bloque.
{% endtag1 %}
```

Y los argumentos son fácilmente accesibles en el método `process`:

```js
module.exports = {
    blocks: {
        tag1: {
            process: function(block) {
                // block.args equals ["argumento 1", "argumento 2"]
                // block.kwargs equals { "name": "Prueba" }
            }
        }
    }
};
```

## Manejo de subbloques

Un bloque definido se puede analizar en diferentes subbloques, por ejemplo, consideremos la fuente:

```twig
{% myTag %}
    Cuerpo principal
    {% subblock1 %}
    Cuerpo del sub-block 1
    {% subblock 2 %}
    Cuerpo del sub-block 2
{% endmyTag %}
```
