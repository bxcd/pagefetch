# pagefetch

An app for demonstrating pagination of data fetched from a remote datasource with different datasource key types.

## Attribution

This app is built with Android Jetpack and Square Retrofit

## Prerequisites

1. Clone the repository by running [git clone https://github.com/bxcd/pagefetch.git]
2. In Android Studio, select a debug build variant from the BuildVariants tab or from the Build menu
3. Add a string resource 'base_url' of https://api.data.charitynavigator.org
4. Follow instructions at [Charity Navigator](http://api.charitynavigator.org/) to register an app ID and key.
5. Create a git-ignored key file with string resources 'app_id' and 'app_key' populated with your registered values.
6. Run the app.

## Description

**Flavors**

Scrollpage:
* Fetches and paginates sorted remote list data directly from remote source
* Page size can be asynchronously updated by controller
* Controller orientation can be updated by menu option
* Datasource key type can be updated by menu option

Singlepage:  
* Fetches and persists single page to, and loads sorted list from, local database
* Page size can be asynchronously updated by controller
* Controller orientation can be updated by menu option

## Illustration

##### Increment, decrement, or enter the page size by pressing the controller.
<p>
<img src="https://github.com/bxcd/pagefetch/blob/main/assets/1.png?raw=true" width="150">
<img src="https://github.com/bxcd/pagefetch/blob/main/assets/2.png?raw=true" width="150">
</p>

##### Move the controller to the other side of the screen by pressing the second menu icon.
<p>
<img src="https://github.com/bxcd/pagefetch/blob/main/assets/3.png?raw=true" width="150">
</p>

##### Change the datasource key type by pressing the first menu icon.
<p>
<img src="https://github.com/bxcd/pagefetch/blob/main/assets/4.png?raw=true" width="150">
</p>
<p style="font-size: 10px;">Note: Charity Navigator API is only designed for page-keyed type. Other datasource key types are experimental.</p>