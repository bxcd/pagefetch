# pagefetch

**An app for demonstrating local and remote paging of data fetched from a remote datasource**

## Attribution

This app is built with Android Jetpack and Square Retrofit

## Prerequisites

1. Clone the repository by running [git clone https://github.com/bxcd/pagefetch.git]
2. In Android Studio, select a debug build variant from the BuildVariants tab or from the Build menu

## Description

**Flavors**

Singlepage:  
* Fetches and persists single page to, and loads sorted list from, local database
* Pagination size can be asynchronously updated by controller
* Controller orientation can be updated by menu option

Scrollpage:
* Fetches and paginates sorted remote list data without local persistence
* Pagination size can be asynchronously updated by controller
* Controller orientation can be updated by menu option
* Pagination type can be updated by menu option

# Prerequisites

* 1. Add a string resource 'base_url' of https://api.data.charitynavigator.org
* 2. Follow instructions at [Charity Navigator](http://api.charitynavigator.org/) to register an app ID and key.
* 3. Create a git-ignored key file with string resources 'app_id' and 'app_key' populated with your registered values.
* 4. Run the app.

