<h1 align="center">🤖 ChatBot App</h1>

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-1.9.0-blueviolet?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin">
  <img src="https://img.shields.io/badge/Jetpack%20Compose-1.5-brightgreen?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Jetpack Compose">
  <img src="https://img.shields.io/badge/Clean%20Architecture-%23007ACC.svg?style=for-the-badge" alt="Clean Architecture">
</p>

---

## 📌 Descripción

ChatBot App es una aplicación de mensajería basada en inteligencia artificial, desarrollada en **Kotlin** con **Jetpack Compose** y arquitectura **Clean Architecture**. Permite enviar y recibir mensajes en tiempo real, integrando un motor de procesamiento de texto basado en IA.

---

## 🏗️ Arquitectura

La app sigue el patrón de **Clean Architecture**, separando las responsabilidades en distintas capas:


### 🔹 **Capa de Datos (`data`)**
- Contiene los **DataSources** que acceden a la API.
- Implementa modelos **DTO** y mappers para convertirlos en objetos de dominio.

### 🔹 **Capa de Dominio (`domain`)**
- Define los **Casos de Uso** que encapsulan la lógica de negocio.
- Interfaces de repositorio para desacoplar las dependencias.

### 🔹 **Capa de Presentación (`presentation`)**
- Usa **Jetpack Compose** para la UI declarativa.
- **ViewModels** con `StateFlow` para manejar el estado de la UI.

---

## 🚀 Tecnologías Utilizadas

| Tecnología  | Descripción  |
|------------|-------------|
| 🟣 **Kotlin**  | Lenguaje de programación principal |
| 🟢 **Jetpack Compose** | Framework para UI declarativa |
| 🔵 **Koin** | Inyección de dependencias |
| 🔹 **Retrofit** | Cliente HTTP para consumo de APIs |
| 🔸 **Coroutines** | Manejo de concurrencia y asincronía |
| ⚡ **StateFlow** | Manejo de estados en la UI |
| 🎨 **Material 3** | Diseño moderno basado en Material You |

---

## 📷 Vista Previa

<p align="center">
  <img src="https://github.com/xvirs/ChatBot/blob/main/app/src/main/res/imageProyect/Captura%20de%20pantalla%202025-03-16%20182450.png" alt="Pantalla de ChatBot">
  <img src="https://github.com/xvirs/ChatBot/blob/main/app/src/main/res/imageProyect/Captura%20de%20pantalla%202025-03-16%20182256.png" alt="Pantalla de ChatBot">
</p>

---

## 🛠️ Instalación y Configuración

1. **Clonar el repositorio**  
   ```bash
   git clone https://github.com/tu-usuario/chatbot-app.git
   cd chatbot-app
