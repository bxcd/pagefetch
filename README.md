# pagefetch

An app for demonstrating pagination of data fetched from a remote datasource with different datasource types.

## Attribution

This app is built with Android Jetpack and Square Retrofit

## Prerequisites

1. Clone the repository by running [git clone https://github.com/bxcd/pagefetch.git]
2. In Android Studio, select a debug build variant from the BuildVariants tab or from the Build menu

## Description

**Flavors**

Scrollpage:
* Fetches and paginates sorted remote list data directly from remote source
* Pagination size can be asynchronously updated by controller
* Controller orientation can be updated by menu option
* Datasource type can be updated by menu option

Singlepage:  
* Fetches and persists single page to, and loads sorted list from, local database
* Pagination size can be asynchronously updated by controller
* Controller orientation can be updated by menu option

# Prerequisites

* 1. Add a string resource 'base_url' of https://api.data.charitynavigator.org
* 2. Follow instructions at [Charity Navigator](http://api.charitynavigator.org/) to register an app ID and key.
* 3. Create a git-ignored key file with string resources 'app_id' and 'app_key' populated with your registered values.
* 4. Run the app.

## Illustration

<p>
Increment, decrement, or enter the pagination size by pressing the controller.
<img src="https://github.com/bxcd/pagefetch/blob/main/assets/1.png?raw=true" width="50">
<img src="https://github.com/bxcd/pagefetch/blob/main/assets/2.png?raw=true" width="50">
</p>


<p>
Move the controller to the other side of the screen by pressing the second menu icon.
<img src="https://github.com/bxcd/pagefetch/blob/main/assets/3.png?raw=true" width="50">
</p>

<p>
Change the datasource type by pressing the first menu icon.
<img src="https://github.com/bxcd/pagefetch/blob/main/assets/4.png?raw=true" width="50">
</p>