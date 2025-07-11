# Selenium UI Test - Herokuapp Demo

Proyecto de ejemplo con Selenium + Java + Maven.  
Incluye tests automatizados sobre la página pública: [https://the-internet.herokuapp.com/](https://the-internet.herokuapp.com/)

## Tests incluidos

- Login (Form Authentication)
- Checkboxes
- Dropdown
- File Upload
- Dynamic Loading

## Ejecución

```bash
mvn test
```

# Selenium Scraper - Amazon Disponibilidad

Proyecto de ejemplo con Selenium + Java + Maven.  
Extrae productos de [https://www.amazon.es/](https://www.amazon.es/) y genera un CSV con la disponibilidad.

## ¿Qué hace?

- Busca "RTX 4070"
- Para cada producto de la búsqueda:
  - Si tiene precio → lo marca como "Disponible - XX €"
  - Si no tiene precio → lo marca como "No disponible"
- Guarda un CSV `amazon_availability.csv` con:


## Ejecución

```bash
mvn compile exec:java -Dexec.mainClass="com.github.scraper.AmazonScraperDisponible"

