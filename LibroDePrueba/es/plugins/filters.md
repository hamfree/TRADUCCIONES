# Extender filtros

Los filtros son esencialmente funciones que se pueden aplicar a variables. Se llaman con un operador de canalización (`|`) y pueden aceptar argumentos.

```twig
{{ foo | title }}
{{ foo | join(",") }}
{{ foo | replace("foo", "bar") | capitalize }}
```

## Definiendo un nuevo filtro

Los complementos pueden ampliar los filtros definiendo funciones personalizadas en su punto de entrada bajo el alcance de `filters`.

Una función de filtro toma como primer argumento el contenido a filtrar y debe devolver el nuevo contenido.
Consulte [Contexto y API](./api.md) para obtener más información sobre `this` y la API de HonKit.

```js
module.exports = {
    filters: {
        hello: function(name) {
            return 'Hola '+name;
        }
    }
};
```

El filtro `hello` se puede utilizar en el libro:


```twig
{{ "Aaron"|hello }}, ¿Cómo estas?
```

## Manejo de argumentos de bloque

Se pueden pasar argumentos a filtros:

```twig
Hello {{ "Samy"|fullName("Pesse", man=true}} }}
```

Los argumentos se pasan a la función, los argumentos con nombre se pasan como último argumento (objeto).

```js
module.exports = {
    filters: {
        fullName: function(firstName, lastName, kwargs) {
            var name = firstName + ' ' + lastName;

            if (kwargs.man) name = "Señor " + name;
            else name = "Señora " + name;

            return name;
        }
    }
};
```
