pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AnimalsApp"
include(":app")
include(":data:network")
include(":data:roomdb")
include(":data:sharedpreferences")
include(":domain")
include(":core")
include(":feature:dogs")
include(":feature:reminder")
include(":feature:birds")
include(":feature:cats")

