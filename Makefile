build:
	mvn clean install -DskipTests

develop-run:
	mvn spring-boot:run

local-develop-run:
	docker-compose -f docker-compose/docker-compose.yml up -d
	mvn spring-boot:run

test-run:
	mvn test