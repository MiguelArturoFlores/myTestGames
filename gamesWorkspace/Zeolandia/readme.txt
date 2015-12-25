FUNCIONAMIENTO DEL PURCHASE

en MainDropActivity cuando se inician los listeners, purchasefinal se encarga de llamar al inventario si tiene exito.
el inventario es el que llama al consumeItem y ademas asigna el poder, y finalmente el listener consumtionlistener es 
quien muestra un hud al usuario con las indicaciones de que la compra fue exitosa

el inventario debe llamarse en varias partes de la aplicacion para recibir nuevas actualizaciones de google con posibles 
compras pendientes 