# POO_FinalProject_Ramirez - Ticket Office with Swing
## Integrantes: Michael Ramírez Gómez (cod: 63421) 

El programa tiene como objetivo simular una oficina de venta de tiquetes para eventos, en la cual se puede realizar el registro de los eventos, lugares fisicos, clientes y agregar las localidades de los eventos. El sistema permite la venta y registro de tiquetes para cada uno de los eventos por el cliente seleccionado, se guardan los tiquetes tanto en la oficina de tiquetes, como en el evento y el cliente.

## Descripción General del Sistema

 El alcance del proyecto abarca la venta de tiquetes y la modificación de ciertos atributos de los objetos derivados de las clases principales, teniendo en cuenta aspectos como la capacidad del lugar fisico y algunas restricciones que permiten llevar a cabo una configuración robusta del software, cuidando la eliminación de eventos o localdiades si estos ya cuentan con tiquetes vendidos a los clientes. Ademas, el software guarda la información de la clase principal ticket office al realizar operaciones importantes como agregar clientes, eventos, lugares fisicos(venues) y la venta de tiquetes para no perder la información cuando se abre y se cierra el sistema. Finalmente se puede optar por guardar la lista de clientes, eventos y venues en archivos CSV independientes. 

 El funcionamiento del programa depende del estado en que se encuentra la oficina puesto que si no se tienen venues, clientes o eventos, entonces el funcionamiento del software comenzara por realizar el registro de estos objetos, ya que si bien se podra acceder a todas las caracteristicas, cuando se vaya a realizar un proceso como la compra de tiquetes entonces enviara un mensaje de alerta porque no se tiene eventos o clientes, así el alcance funcional se limita. Por otro lado, cuando ya se hayan agregado objetos de las clases clientes, venues y eventos, es momento de agregar localidades a los eventos, con el fin de separar la venta de tiquetes por localidades. Suponiendo que el estado del programa ya cuenta con los diferentes objetos para su funcionamiento general, entonces se podran realizar cada una de las funcionalidades integradas en cada una de las pantallas. Donde se podran agregar, modificar, listar, buscar y eliminar objetos. 

 Para la realización de las pruebas de funcionamiento del software se diseño una clase denominada DemoData la cual cuenta con datos de demostración para mostrar como funciona el proyecto, si se desea se puede trabajar con estos datos de manera inicial con el fin de comprobar que todas las caracteristicas cumplen con el proposito para el cual fueron diseñadas.

 ## Instrucciones para Ejecutar el Programa

El programa se ejecuta a través del fichero App.java en el cual se indique que si existe el ticketoffice.dat en el origen del proyecto entonces lo abra, en caso contrario que utilice el DemoData, de esta forma siempre que se va a iniciar el sistema lo hara con los datos que se han guardado de forma frecuente con la interacción del sistema y al realizarle los cambios. Pero en caso de que sea la primera vez que se va a ejecutar el programa, entonces se van a cargar los datos de prueba para hacer el test a cada una de las funcionalidades del sistema. 

En este orden de ideas, las instrucciones para ejecutar el programa de forma correcta, es: 

1. Comprobar si en la raiz del sistema esta el documento que se llama ticketoffice.dat para saber si los datos que va a traer el sistema son los de DemoData o datos guardados con anterioridad.
2. Ejecutar el fichero App.java el cual contiene el método main que inicializa todo el sistema. 
3. Esperar a que el sistema se inicialice y al cabo de unos segundos se le mostrara una pantalla con el menú de opciones.
4. Dar click en la opción administrar localidades (es una recomendación que se hace), para poder verificar que los eventos ya tengan lcoalidades asignadas, en caso de que hayan eventos sin localidades se deben añadir con el fin de que se puedan comprar tiquetes. 
5. Comprobar si se tienen clientes registrados, para ello debe dirigirse a administrar clientes, en la ventana que se abre, aparece la lista de los clientes que ya se tienen registrados en la plataforma. 
6. Explorar el sistemas según las necesidades que se tengan, si se desea registrar nuevos venues, eventos, o clientes, el sistema le permitira realizarlo a partir de los botones que indican cada acción según la ventana en la que se encuentre. 
7. Al finalizar si desea conservar en un archivo CSV los datos de los clientes, eventos o venues, puede realizarlo dando clic en el botón que dice "Exportar CSV", allí podra seleccionar los datos que desea exportar. 

## Ejemplo de Entrada/Salida







## Enlace Diagrama de Clase

https://github.com/MichaelIngSis/POO_FinalProject_Ramirez/blob/b64f2f02cabb80f6463cc43cd6102d68b778c98b/Diagrama_de_Clase.drawio.png
