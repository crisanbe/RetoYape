# RetoYape
# Qué es este proyecto?
Proyecto
[https://github.com/crisanbe/RetoYape.git](https://github.com/crisanbe/RetoYape.git/).

![](https://i.imgur.com/hw8aZBFm.png)![](https://i.imgur.com/0ntHFBym.png)![](https://i.imgur.com/2RTwqWLm.png)![](https://i.imgur.com/nAWtVHVm.png)![](https://i.imgur.com/3Rsd0Skm.png)

# Características principales
1. Kotlin
1. MVVM
1. Jetpack Compose
1. MutableStateOf
1. MutableSharedFlow 
1. UIEvent 
	- Simpler
1. Hilt
1. Navigation Compose
1. Retrofit
1. Custom Fonts
	- Toggle between themes

# Funciones de composición
1. Snackbars
3. Theming
4. Fonts
5. Colors
	- creating 
7. ConstraintLayout
8. Rows
9. Columns
10. Scaffold
11. AppBar
12. Circular Progress Indicator

# Pruebas
1. Junit
3. Mockito
4. Sonarqube
5. Owasp Dependence check
6. Jacoco coverage.

#  SonarQube
1. SonarQube es una plataforma de código abierto para evaluar y analizar la calidad del código fuente. Proporciona métricas detalladas, informes y visualizaciones para ayudar a los equipos de desarrollo a identificar y corregir problemas de calidad de código.
	1. Comando ./gradlew sonarqube
![](https://i.imgur.com/WQUSlaO.png)

# Owasp Dependency Check:
1. Herramienta que escanea las dependencias de tu proyecto en busca de vulnerabilidades conocidas. Ayuda a identificar componentes de software con posibles problemas de seguridad.
	1. Comando ./gradlew dependencyCheckAnalyze 
	 http://localhost:63342/RetoListaDeRecetas/build/reports/dependency-check-report.html
![](https://i.imgur.com/y3uW826.png)

	1. Comando ./gradlew dependencies    para  verificar las librerias especiales o trasnversales.

# JaCoCo (Java Code Coverage):
1. JaCoCo es una herramienta de análisis de cobertura de código para proyectos Java. Te permite evaluar qué porcentaje de tu código está siendo ejecutado durante las pruebas, lo que es útil para asegurarse de que las pruebas cubran adecuadamente tu código.
 Integración en Android Studio:
JaCoCo se integra fácilmente con Gradle (el sistema de construcción de Android).
Puedes configurar tu proyecto para que las pruebas generen informes de cobertura de código y luego ver los resultados en Android Studio.
	1. Comando ./gradlew koverMergedReport   file:///Users/macbook/StudioProjects/RetoListaDeRecetas/build/reports/kover/merged/html/ns-11/sources/source-5.html

![](https://i.imgur.com/5Ptkunp.png)

#  Flow?
1. Flow 
	1. Flow es genial. es una característica de Kotlin coroutines que proporciona una forma asincrónica y reactiva de trabajar con secuencias de datos.
	  El método getListRecipeUseCase devuelve un Flow ya que se realiza una operación collect en el resultado. Esto indica que probablemente getListRecipeUseCase retorna un flujo de datos asincrónico que el ViewModel está consumiendo.
	1.(https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/).
1. StateFlow Semántica de Estado: 
	1. StateFlow  está diseñado específicamente para representar un estado mutable y proporcionar un flujo de eventos que notifica a los suscriptores cuando el estado cambia. Esto es útil cuando necesitas mantener y compartir un estado mutable en toda tu aplicación. (https://developer.android.com/reference/kotlin/androidx/compose/runtime/MutableState) in viewmodels.
1. StateFlow Semántica de Estado: 
	1. SharedFlow  está diseñado para emitir eventos o notificaciones a sus suscriptores. Es adecuado cuando necesitas comunicar eventos que no necesariamente están relacionados con un estado mutable. (https://developer.android.com/reference/kotlin/androidx/compose/runtime/MutableState) in viewmodels.


# References
1. https://github.com/android/compose-samples
1. https://developer.android.com/jetpack/compose
1. https://developer.android.com/jetpack/compose/state
