default: build-recipe-scraper
all: build-recipe-scraper

build-recipe-scraper:
	@echo "\n\033[0;32mBuilding recipe-scraper service...\033[0m\n"
	@./gradlew -b build.gradle --warning-mode all --info --stacktrace build

clean:
	@echo "\n\033[0;32mCleaning previous builds...\033[0m\n"
	@./gradlew -b build.gradle clean
