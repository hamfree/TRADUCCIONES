# Crear y publicar un complemento

Un complemento HonKit es un paquete de Node publicado en NPM que sigue una convención definida.

## Estructura

### package.json

`package.json` es un formato de manifiesto para describir **módulos de Node.js**. Los complementos de HonKit se crean sobre los módulos de Node. Declara dependencias, versión, propiedad y otra información necesaria para ejecutar un complemento en HonKit. Este documento describe el esquema en detalle.

Un manifiesto de complemento `package.json` también puede contener detalles sobre la configuración requerida. El esquema de configuración se define en el campo `honkit` de `package.json` (este campo sigue las pautas de [JSON-Schema](http://json-schema.org)):

```js
{
    "name": "honkit-plugin-mitest",
    "version": "0.0.1",
    "description": "Este es mi primer complemento de HonKit",
    "engines": {
        "honkit": ">1.x.x"
    },
    "honkit": {
        "properties": {
            "myConfigKey": {
                "type": "string",
                "default": "esto es el valor por defecto",
                "description": "¡esto define mi maravillosa configuración"
            }
        }
    }
}
```

Puede obtener más información sobre `package.json` en [la documentación de NPM](https://docs.npmjs.com/files/package.json).

El **nombre del paquete** debe comenzar con los siguientes patrones:

- `@<scope>/honkit-plugin-`
- `honkit-plugin-`
- `@<scope>/gitbook-plugin-`
- `gitbook-plugin-`

Además, los **motores de paquetes** deben contener `honkit` o `gitbook`.

### index.js

`index.js` es el punto de entrada principal del tiempo de ejecución de su complemento:

```js
module.exports = {
    // Mapa de ganchos
    hooks: {},

    // Mapa de bloques nuevos
    blocks: {},

    // Mapa de filtros nuevos
    filters: {}
};
```

## Publicar tu complemento

Los complementos de HonKit se pueden publicar en [NPM](https://www.npmjs.com).

Para publicar un nuevo complemento, debe crear una cuenta en [npmjs.com](https://www.npmjs.com) y luego publicarlo desde la línea de comando:

```bash
npm publish
```

## Complementos privados

Los complementos privados se pueden alojar en GitHub e incluir usando URL `git`:

```json
{
    "plugins": [
        "myplugin@git+https://github.com/MyCompany/myhonkitplugin.git#1.0.0"
    ]
}
```
