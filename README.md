# 📌 HabitFlow API

## 🧠 Descripción

HabitFlow API es un backend desarrollado con Spring WebFlux que permite gestionar hábitos personales, registrar su cumplimiento diario y controlar rachas consecutivas.

El sistema está diseñado bajo una arquitectura hexagonal (Ports & Adapters), lo que permite separar la lógica de negocio de la infraestructura, facilitando el mantenimiento y la escalabilidad.

---

## 🚀 Funcionalidades

* Gestión de usuarios
* CRUD de hábitos
* Clasificación de hábitos por categorías
* Registro diario de cumplimiento
* Cálculo automático de rachas

---

## 🧱 Arquitectura

El proyecto sigue una arquitectura hexagonal, organizada en:

* **Domain:** Modelos y lógica de negocio
* **Ports:** Interfaces de entrada y salida
* **Adapters:** Implementaciones (API REST, base de datos)

---

## ⚙️ Tecnologías

* Spring WebFlux (programación reactiva)
* Java
* Base de datos MongoDB

---

## 📊 Modelo de datos

El sistema se basa en las siguientes entidades:

* Usuario
* Hábito
* Categoría
* Registro de hábito
* Racha

<img width="584" height="1068" alt="DiagramaER" src="https://github.com/user-attachments/assets/f5acab32-a1fe-46ba-acfb-927d80b555f4" />

---

## ▶️ Ejecución

1. Clonar el repositorio:

```bash
git clone https://github.com/krmanrique/habit-tracker.git
```

2. Ingresar al proyecto:

```bash
cd habit-tracker
```

3. Ejecutar la aplicación:

```bash
./mvnw spring-boot:run
```

---

## 📌 Notas

Este proyecto está enfocado únicamente en el backend y no incluye interfaz gráfica.

---

## 👨‍💻 Autores

* [Juan Reina](https://github.com/juanreina19)
* [Kevin Manrique](https://github.com/krmanrique)
