IMAGE := laurihuotari/coro-bot
TAG ?= dev
VERSION_NUMBER := sha-$(shell git rev-parse HEAD 2> /dev/null)

.PHONY: build
build:
	gradle ktlintFormat
	gradle clean test assemble

.PHONY: run
run: build
	java -Xms512m -Xmx512m -XX:+ExitOnOutOfMemoryError -jar build/libs/spring-kotlin-bot-1.0.jar

.PHONY: publish
publish: docker
	docker push $(IMAGE):$(TAG)
