# Prueba tu complemento

## Prueba tu complemento localmente

Es posible probar el complemento en su libro antes de publicarlo usando [npm link](https://docs.npmjs.com/cli/link).

En la carpeta del complemento, ejecute:

```bash
 npm link
```

Luego en la carpeta de tu libro:

```bash
 npm link honkit-plugin-<nombre del complemento>
```

## Pruebas unitarias en Travis

[gitbook-tester](https://github.com/todvora/gitbook-tester) facilita la escritura de pruebas unitarias **Node.js/Mocha** para sus complementos. Usando [Travis.org](https://travis.org), se pueden ejecutar pruebas en cada confirmación/etiqueta.

[honkit-tester](https://github.com/vowstar/honkit-tester) con el tiempo, en algunos casos, gitbook-tester ya no funciona correctamente en la última versión de nodejs. Este es un puerto de gitbook-tester, que podría funcionar en la versión LTS de nodejs v14.x v16.x v18.x y usar el motor honkit en lugar de gitbook para ejecutar la prueba.
