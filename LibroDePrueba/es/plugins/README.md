# Complementos

Los complementos son la mejor manera de ampliar las funcionalidades de HonKit (libro electrónico y sitio web). Existen complementos para hacer muchas cosas: brindar soporte para mostrar fórmulas matemáticas, realizar un seguimiento de las visitas mediante Google Analytic, etc.

## ¿Cómo encontrar complementos?

Los complementos se pueden buscar fácilmente en [npmjs.com](https://www.npmjs.com/).

Busque en npm con palabras clave: `gitbook-plugin` o `honkit-plugin`

## ¿Cómo instalar un complemento?

Una vez que encuentre un complemento que quiera instalar, necesita agregarlo a su `book.json`:

```json
{
    "plugins": ["miPlugin", "otroPlugin"]
}
```

Puede también especificar una versión concreta usando: `"myPlugin@0.3.1"`. De forma predeterminada HonKit resolverá la última versión del complemento compatible con la versión actual de HonKit

## Configurar complementos

Las configuraciones específicas de los complementos se almacenan en `pluginsConfig`. Debe consultar la documentación del complemento para obtener detalles sobre las opciones disponibles.
