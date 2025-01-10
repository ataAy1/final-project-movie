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
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
        maven {


    }


}

rootProject.name = "FinalProject"
include(":app")
include(":data")
include(":core:domain")
include(":core:util")
include(":features:feature-signin")
include(":features:feature-signup")
include(":features:feature-home")
include(":features:feature-welcome")
include(":features:feature-profile")
include(":features:feature-orders")
include(":features:feature-address")
include(":features:feature-cards")
include(":features:feature-settings")
include(":features:feature-basket")
include(":features:feature-movie-detail")
include(":features:feature-favorites")
include(":features:feature-payment")
include(":features:feature-coupon")
