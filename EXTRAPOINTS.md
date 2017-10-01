#Puntos Extras

Para resolver los puntos extras de :

- Dar alguna solución sobre qué pasa con una llamada cuando no hay ningún empleado libre.
- Dar alguna solución sobre qué pasa con una llamada cuando entran más de 10 llamadas concurrentes.

Se consideró utilizar un [PriorityBlockingQueue](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/PriorityBlockingQueue.html) el cual, saca a los empleados disponibles de una queue priorizada, para garantizar que los primeros en atender una llamada sean los Operadores, luego los supervisores y por ultimo los directores. Ademas, si no hay empleados en la queue, el metodo [take](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/PriorityBlockingQueue.html#take--) bloquea el flujo hasta que algun empleado vuleva a la queue para continuar el flujo.

Por eso, en ambos casos, tanto si no hay un empleado libre o hay 10 llamadas concurrentes, siempre que haya una empleado disponible, se atiende la llamada, sino quedará la llamada a la espera de que algun empleado vuelva a estar disponible.